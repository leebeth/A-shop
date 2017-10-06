package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

import android.support.v4.widget.SlidingPaneLayout;

import com.tienda.a_shop.R;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
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
    private List<CategoriaXGastoMes> categoriasMesActual;
    private TextView gasto;
    private TextView ingreso;
    private TextView total;
    private ReportGeneratorTask task;
    private boolean writeExternalStorage;
    private GastoMes gastoActual;
    private ICategoriaPresenter categoriaPresenter;
    private SlidingPaneLayout panelSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        categoriaPresenter = new CategoriaPresenter((IApp)getApplication(), this);

        listaProductos = (ListView) findViewById(R.id.listaProductos);
        gasto = (TextView) findViewById(R.id.txtTotalGastos);
        ingreso = (TextView) findViewById(R.id.txtTotalIngreso);
        total = (TextView) findViewById(R.id.txtTotal);

        categoriaPresenter.obtenerGastoActual();

        categoriaPresenter.listarCategoriasMesActual();

        registerForContextMenu(listaProductos);
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent i = new Intent(ListaCategoriasActivity.this, ListaItemsProductosActivity.class);
                i.putExtra("idProducto", categoriasMesActual.get(position).getId());
                i.putExtra("nombreProducto", categoriasMesActual.get(position).getCategoria().getNombre());
                i.putExtra("estimadoProducto", categoriasMesActual.get(position).getEstimado());
                startActivityForResult(i, REQUEST_DETAIL);
            }
        });

        //agregar producto
        FloatingActionButton agregarCategoria = (FloatingActionButton) findViewById(R.id.agregarProducto);
        agregarCategoria.setOnClickListener(new View.OnClickListener() {

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
                com.tienda.a_shop.entities.CategoriaXGastoMes p = categoriasMesActual.get(info.position);
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
                String nombreN = data.getExtras().getString("nombreN");
                int estimado = Integer.parseInt(data.getExtras().getString("estimado"));

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
        String nomCategoria = categoriasMesActual.get(posicion).getCategoria().getNombre();
        categoriaPresenter.eliminarCategoria(nomCategoria);
        categoriaPresenter.actualizarLista();
    }

    @Override
    public void actualizarLista(List<CategoriaXGastoMes> categorias) {
        categoriasMesActual = categorias;
        if (categoriasMesActual.isEmpty()) {
            categoriaPresenter.agregarCategoria("Ingresos", 0);
        }
        ArrayAdapter<com.tienda.a_shop.entities.CategoriaXGastoMes> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoriasMesActual);
        listaProductos.setAdapter(adapter);
        actualizarListaResumen();
    }

    @Override
    public void obtenerGastoMesActual(GastoMes gastoMesActual)
    {
        this.gastoActual = gastoMesActual;
    }

    private void actualizarListaResumen() {
        int totalAux = 0;
        for (int i = 1; i < categoriasMesActual.size(); i++) {
            totalAux += categoriasMesActual.get(i).getTotal();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        gasto.setText(getString(R.string.gastos) + formatter.format(totalAux));
        ingreso.setText(getString(R.string.ingresos) + formatter.format(categoriasMesActual.get(0).getTotal()));
        total.setText(getString(R.string.total) + formatter.format(categoriasMesActual.get(0).getTotal() - totalAux));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_producto_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_crear_reporte:
                task = new ReportGeneratorTask();
                task.setActivity(this);
                CategoriaXGastoMes[] array = new CategoriaXGastoMes[categoriasMesActual.size()];
                task.execute(categoriasMesActual.toArray(array));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtil.WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.

                //writeExternalStorage true
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

                //writeExternalStorage false
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                writeExternalStorage = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;

                task.setWaiting(false);
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean isWriteExternalStorage() {
        return writeExternalStorage;
    }


}
