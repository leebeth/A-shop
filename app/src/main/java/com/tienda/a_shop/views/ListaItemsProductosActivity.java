package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tienda.a_shop.R;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.presenters.CategoriaPresenter;
import com.tienda.a_shop.presenters.ItemPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;
import com.tienda.a_shop.presenters.interfaces.presenters.IItemPresenter;
import com.tienda.a_shop.views.interfaces.ItemViewOptions;

import java.text.NumberFormat;
import java.util.List;


public class ListaItemsProductosActivity extends ItemViewOptions {


    private static final int REQUEST_TEXT = 0;
    private static final int REQUEST_ADD = 1;

    private List<Item> items;
    private ListView listaProductos;
    private long idCategoriaGastoMes;
    private int estimado;
    private NumberFormat formatter;

    private IItemPresenter itemPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_de_venta);

        itemPresenter = new ItemPresenter((IApp) getApplication(), this);

        formatter = NumberFormat.getCurrencyInstance();

        idCategoriaGastoMes = getIntent().getLongExtra("idProducto", 0L);
        String nombreProducto = getIntent().getStringExtra("nombreProducto");
        estimado = getIntent().getIntExtra("estimadoProducto", 0);

        ((TextView) findViewById(R.id.txtEstimado)).setText(String.format(getString(R.string.estimado), formatter.format(estimado)));
        ((TextView) findViewById(R.id.label_carrito)).setText(nombreProducto);
        listaProductos = (ListView) findViewById(R.id.carrito_lista);

        Button cancelar = (Button) findViewById(R.id.carrito_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        itemPresenter.obtenerItems(idCategoriaGastoMes);

        Button agregarItem = (Button) findViewById(R.id.carrito_ok);
        agregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaItemsProductosActivity.this, AgregarItemGastoActivity.class);
                i.putExtra("idProducto", idCategoriaGastoMes);
                i.putExtra("totalGasto", calcularPrecioTotal());
                startActivityForResult(i, REQUEST_ADD);
            }
        });
        registerForContextMenu(listaProductos);
    }

    private int calcularPrecioTotal() {
        int tot = 0;
        if (items != null) {
            for (Item i : items) {
                tot += i.getValor();
            }
        }
        return tot;
    }

    private void actualizarPrecioTotal() {
        int tot = 0;
        if (items != null) {
            for (Item i : items) {
                tot += i.getValor();
            }
        }
        ((TextView) findViewById(R.id.txtReal)).setText(
                String.format(getString(R.string.real), formatter.format(tot)));
        ((TextView) findViewById(R.id.txtDisponible)).setText(
                String.format(getString(R.string.disponible), formatter.format(estimado - tot)));
    }

    public String eliminarItemCarrito(int pos) {
        Toast.makeText(getApplicationContext(), "No implementado", Toast.LENGTH_SHORT).show();
        return "";
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_producto_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_editar_producto:
                Item itemToUpdate = items.get(info.position);
                Intent i = new Intent(this, EditarItemActivity.class);
                i.putExtra("Id", itemToUpdate.getId());
                i.putExtra("Nombre", itemToUpdate.getNombre());
                i.putExtra("Valor", itemToUpdate.getValor());
                i.putExtra("CategoriaXGastoMesId", itemToUpdate.getCategoriaXGastoMesId());
                startActivityForResult(i, REQUEST_TEXT);
                return true;
            case R.id.action_eliminar_producto:
                eliminarItemCarrito(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD || requestCode == REQUEST_TEXT) {
            if (resultCode == Activity.RESULT_OK) {
                itemPresenter.obtenerItems(idCategoriaGastoMes);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.carrito_de_venta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void actualizarLista(List<Item> list) {
        items = list;
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        listaProductos.setAdapter(adapter);
        actualizarPrecioTotal();
    }
}
