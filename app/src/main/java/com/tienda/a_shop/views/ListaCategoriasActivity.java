package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.BDProductos;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.presenters.CategoriaPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;
import com.tienda.a_shop.tasks.ReportGeneratorTask;
import com.tienda.a_shop.utils.PermissionsUtil;
import com.tienda.a_shop.views.interfaces.CategoriaViewOptions;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Lorena on 10/10/2014.
 * Lista de Categorias
 */
public class ListaCategoriasActivity extends CategoriaViewOptions {

    private static final String TAG = "ListaCategoriasActivity";

    public static final int REQUEST_TEXT = 0;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_DETAIL = 2;
    private ListView listaProductos;
    private List<com.tienda.a_shop.entities.CategoriaXGastoMes> productos;
    private ImageView agregarProducto;
    private BDProductos bdProductos;
    private TextView gasto;
    private TextView ingreso;
    private TextView total;
    private ReportGeneratorTask task;
    private boolean writeExternalStorage;
    private com.tienda.a_shop.entities.GastoMes gastoActual;
    private ICategoriaPresenter categoriaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        categoriaPresenter = new CategoriaPresenter((IApp)getApplication(), this);

        bdProductos = new BDProductos(getApplicationContext(), (App)getApplication());
        listaProductos = (ListView) findViewById(R.id.listaProductos);
        gasto = (TextView) findViewById(R.id.txtTotalGastos);
        ingreso = (TextView) findViewById(R.id.txtTotalIngreso);
        total = (TextView) findViewById(R.id.txtTotal);

        gastoActual = bdProductos.getGastoActual();

        categoriaPresenter.listarCategoriasMesActual();

        registerForContextMenu(listaProductos);
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent i = new Intent(ListaCategoriasActivity.this, ListaItemsProductosActivity.class);
                i.putExtra("idProducto", productos.get(position).getCategoria().getId());
                i.putExtra("nombreProducto", productos.get(position).getCategoria().getNombre());
                i.putExtra("estimadoProducto", productos.get(position).getEstimado());
                i.putExtra("idGastoMes", gastoActual.getId());
                startActivityForResult(i, REQUEST_DETAIL);
            }
        });

        //agregar producto
        agregarProducto = (ImageView) findViewById(R.id.agregarProducto);
        agregarProducto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaCategoriasActivity.this, AgregarCategoriaActivity.class);
                i.putExtra("idGastoMes", gastoActual.getId());
                startActivityForResult(i, REQUEST_ADD);
            }
        });
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
                Intent i = new Intent(ListaCategoriasActivity.this, EditarProductoActivity.class);
                com.tienda.a_shop.entities.CategoriaXGastoMes p = productos.get(info.position);
                i.putExtra("nombre", p.getCategoria().getNombre());
                i.putExtra("estimado", p.getEstimado() + "");
                startActivityForResult(i, REQUEST_TEXT);
                return true;
            case R.id.action_eliminar_producto:
                eliminarProducto(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TEXT) {
            if (resultCode == Activity.RESULT_OK) {
                String nombre = data.getExtras().getString("nombre");
                String nombreN = data.getExtras().getString("nombreN").toString();
                int estimado = Integer.parseInt(data.getExtras().getString("estimado").toString());

                editarProducto(nombreN, nombre, estimado);
            }
        }
        if (requestCode == REQUEST_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                categoriaPresenter.actualizarLista();
            }
        }
        if (requestCode == REQUEST_DETAIL) {
            categoriaPresenter.actualizarLista();
        }
    }

    private void editarProducto(String nombreN, String nombre, int estimado) {
        categoriaPresenter.actualizarCategoría(nombreN, nombre, estimado);
        categoriaPresenter.actualizarLista();
    }

    public void eliminarProducto(int posicion) {
        String nomCategoria = productos.get(posicion).getCategoria().getNombre();
        categoriaPresenter.eliminarCategoria(nomCategoria);
        categoriaPresenter.actualizarLista();
    }

    @Override
    public void actualizarLista(List<CategoriaXGastoMes> categorias) {
        productos = categorias;
        if (productos.isEmpty()) {
            categoriaPresenter.agregarCategoria("Ingresos", 0);
        }
        ArrayAdapter<com.tienda.a_shop.entities.CategoriaXGastoMes> adapter = new ArrayAdapter<com.tienda.a_shop.entities.CategoriaXGastoMes>(this, android.R.layout.simple_spinner_dropdown_item, productos);
        listaProductos.setAdapter(adapter);
        actualizarListaResumen();
    }

    private void actualizarListaResumen() {
        int totalAux = 0;
        for (int i = 1; i < productos.size(); i++) {
            totalAux += productos.get(i).getTotal();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        gasto.setText(getString(R.string.gastos) + formatter.format(totalAux));
        ingreso.setText(getString(R.string.ingresos) + formatter.format(productos.get(0).getTotal()));
        total.setText(getString(R.string.total) + formatter.format(productos.get(0).getTotal() - totalAux));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_producto_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = "";
        String tittle = "";
        switch (item.getItemId()){
            case R.id.action_crear_reporte:
                task = new ReportGeneratorTask();
                task.setActivity(this);
                CategoriaXGastoMes[] array = new CategoriaXGastoMes[productos.size()];
                task.execute(productos.toArray(array));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtil.WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    writeExternalStorage = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    writeExternalStorage = false;
                }
                task.setWaiting(false);
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean isWriteExternalStorage() {
        return writeExternalStorage;
    }


}
