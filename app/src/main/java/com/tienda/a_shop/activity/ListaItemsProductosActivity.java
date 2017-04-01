package com.tienda.a_shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.BDProductos;
import com.tienda.a_shop.domain.ItemGasto;

import java.text.NumberFormat;
import java.util.ArrayList;


public class ListaItemsProductosActivity extends Activity {

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_DETAIL = 2;

    private ArrayList<ItemGasto> items;
    private ListView listaProductos;
    private Button agregarItem;
    private Button editar;
    private Button cancelar;
    private int idProducto;
    private BDProductos bdProductos;
    private int estimado;
    private NumberFormat formatter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_de_venta);

        bdProductos = new BDProductos(getApplicationContext());
        formatter = NumberFormat.getCurrencyInstance();

        items = new ArrayList<ItemGasto>();
        idProducto = getIntent().getIntExtra("idProducto", 0);
        String nombreProducto = getIntent().getStringExtra("nombreProducto");
        estimado = getIntent().getIntExtra("estimadoProducto", 0);

        ((TextView) findViewById(R.id.txtEstimado)).setText(getString(R.string.estimado) + formatter.format(estimado));
        ((TextView) findViewById(R.id.label_carrito)).setText(nombreProducto);
        listaProductos = (ListView) findViewById(R.id.carrito_lista);
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();

            }
        });

        cancelar = (Button) findViewById(R.id.carrito_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        actualizarLista();

        agregarItem = (Button) findViewById(R.id.carrito_ok);
        agregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaItemsProductosActivity.this, AgregarItemGastoActivity.class);
                i.putExtra("idProducto", idProducto);
                i.putExtra("totalGasto", calcularPrecioTotal());
                startActivityForResult(i, REQUEST_ADD);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private int calcularPrecioTotal() {
        int tot = 0;
        if (items != null) {
            for (ItemGasto i : items) {
                tot += i.getValor();
            }
        }
        return tot;
    }

    private void actualizarPrecioTotal() {
        int tot = 0;
        if (items != null) {
            for (ItemGasto i : items) {
                tot += i.getValor();
            }
        }
        ((TextView) findViewById(R.id.txtReal)).setText(getString(R.string.real) + formatter.format(tot));
        ((TextView) findViewById(R.id.txtDisponible)).setText(getString(R.string.disponible) + formatter.format(estimado - tot));
    }

    public String eliminarItemCarrito(int pos) {
        String nombre = items.get(pos).getNombre();
        items.remove(pos);
        ArrayAdapter<ItemGasto> adapter = new ArrayAdapter<ItemGasto>(this, android.R.layout.simple_spinner_dropdown_item, items);
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
                actualizarLista();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void actualizarLista() {
        items = bdProductos.listaDetalleGasto(idProducto);
        ArrayAdapter<ItemGasto> adapter = new ArrayAdapter<ItemGasto>(this, android.R.layout.simple_spinner_dropdown_item, items);
        listaProductos.setAdapter(adapter);
        actualizarPrecioTotal();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Items Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.tienda.a_shop/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Items Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.tienda.a_shop/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
