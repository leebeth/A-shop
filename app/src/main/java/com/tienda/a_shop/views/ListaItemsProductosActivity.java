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
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.presenters.ItemPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.IItemPresenter;
import com.tienda.a_shop.views.interfaces.ItemViewOptions;

import java.text.NumberFormat;
import java.util.List;


public class ListaItemsProductosActivity extends ItemViewOptions {

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_DETAIL = 2;

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

        ((TextView) findViewById(R.id.txtEstimado)).setText(getString(R.string.estimado) + formatter.format(estimado));
        ((TextView) findViewById(R.id.label_carrito)).setText(nombreProducto);
        listaProductos = (ListView) findViewById(R.id.carrito_lista);
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();
            }
        });

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
        ((TextView) findViewById(R.id.txtReal)).setText(getString(R.string.real) + formatter.format(tot));
        ((TextView) findViewById(R.id.txtDisponible)).setText(getString(R.string.disponible) + formatter.format(estimado - tot));
    }

    public String eliminarItemCarrito(int pos) {
        String nombre = items.get(pos).getNombre();
        items.remove(pos);
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        listaProductos.setAdapter(adapter);
        return nombre;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.carrito_de_venta, menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                itemPresenter.obtenerItems(idCategoriaGastoMes);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_editar:

                Toast.makeText(getApplicationContext(), "editando el item " + info.id, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_eliminar:
                String nombre = eliminarItemCarrito(info.position);
                Toast.makeText(getApplicationContext(), "Eliminado el item " + nombre + ".", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
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
