package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tienda.a_shop.R;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.presenters.CategoriaPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;
import com.tienda.a_shop.tasks.ReportGeneratorTask;
import com.tienda.a_shop.utils.CategoriaGastoMesUtils;
import com.tienda.a_shop.utils.Comparators;
import com.tienda.a_shop.utils.PermissionsUtil;
import com.tienda.a_shop.views.interfaces.CategoriaViewOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lorena on 10/10/2014.
 * Lista de Categorias
 */
public class MesActivity extends CategoriaViewOptions {

    private static final String TAG = "MesActivity";

    public static final int REQUEST_TEXT = 0;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_DETAIL = 2;
    private static final String PREFS = "PREFS";
    private static final String SALARY_KEY = "salary_key";
    private ListView listaProductos;
    private List<CategoriaXGastoMes> categoriasMesActual;
    private TextView gasto;
    private TextView salario;
    private TextView banco;
    private TextView ingreso;
    private TextView total;
    private TextView gastoEstimado;
    private TextView totalDisponible;
    private ReportGeneratorTask task;
    private boolean writeExternalStorage;
    private GastoMes gastoActual;
    private ICategoriaPresenter categoriaPresenter;
    private ArrayAdapter<CategoriaXGastoMes> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<CategoriaXGastoMes>());

        categoriaPresenter = new CategoriaPresenter((IApp)getApplication(), this);

        setupViewComponents();

        categoriaPresenter.obtenerGastoActual();

        categoriaPresenter.listarCategoriasMesActual();

        registerForContextMenu(listaProductos);
        setupListeners();
    }

    private void setupListeners() {
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent i = new Intent(MesActivity.this, ListaItemsProductosActivity.class);
                i.putExtra("idIngresos", categoriasMesActual.get(0).getId());
                i.putExtra("totalIngresos", CategoriaGastoMesUtils.getTotal(categoriasMesActual.get(0)));
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
                Intent i = new Intent(MesActivity.this, AgregarCategoriaActivity.class);
                i.putExtra("idGastoMes", gastoActual.getId());
                startActivityForResult(i, REQUEST_ADD);
            }
        });
    }

    private void setupViewComponents() {
        listaProductos = (ListView) findViewById(R.id.listaProductos);
        salario = (TextView) findViewById(R.id.txtSalario);
        banco = (TextView) findViewById(R.id.txtCuenta);
        gasto = (TextView) findViewById(R.id.txtTotalGastos);
        ingreso = (TextView) findViewById(R.id.txtTotalIngreso);
        total = (TextView) findViewById(R.id.txtTotal);
        gastoEstimado = (TextView) findViewById(R.id.txtGastoEstimado);
        totalDisponible = (TextView) findViewById(R.id.txtTotalDisponible);
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
                Intent i = new Intent(MesActivity.this, EditarProductoActivity.class);
                com.tienda.a_shop.entities.CategoriaXGastoMes p = categoriasMesActual.get(info.position);
                i.putExtra("nombre", p.getCategoria().getNombre());
                i.putExtra("estimado", p.getEstimado() + "");
                i.putExtra("orden", p.getCategoria().getOrden() + "");
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
                if(data.getExtras() != null){
                    String nombre = data.getExtras().getString("nombre");
                    String nombreN = data.getExtras().getString("nombreN");
                    double estimado = Double.parseDouble(data.getExtras().getString("estimado"));
                    int orden = Integer.parseInt(data.getExtras().getString("orden"));
                    editarProducto(nombreN, nombre, estimado, orden);
                }
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

    private void editarProducto(String nombreN, String nombre, double estimado, int orden) {
        categoriaPresenter.actualizarCategoría(nombreN, nombre, estimado, orden);
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
            categoriaPresenter.agregarCategoria("Ingresos", 0, 0);
            categoriaPresenter.actualizarLista();
        }

        Collections.sort(categoriasMesActual, Comparators.getCategoryMonthComparator());

        adapter.clear();
        adapter.addAll(categoriasMesActual);
        adapter.notifyDataSetChanged();

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
        int totalEstimado = 0;
        for (int i = 1; i < categoriasMesActual.size(); i++) {
            totalAux += CategoriaGastoMesUtils.getTotal(categoriasMesActual.get(i));
            totalEstimado += categoriasMesActual.get(i).getEstimado();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        gasto.setText(getString(R.string.gastos) + formatter.format(totalAux));
        double totalIngresos = CategoriaGastoMesUtils.getTotal(categoriasMesActual.get(0));
        ingreso.setText(getString(R.string.ingresos) + formatter.format(totalIngresos));
        total.setText(getString(R.string.total) + formatter.format(totalIngresos - totalAux));
        gastoEstimado.setText(getString(R.string.gastos) + formatter.format(totalEstimado));
        SharedPreferences shared = getSharedPreferences(PREFS, MODE_PRIVATE);
        float salarioV = (shared.getFloat(SALARY_KEY, 0));
        salario.setText(String.format(getString(R.string.salaryT), formatter.format(salarioV)) );
        totalDisponible.setText(getString(R.string.total_disponible) + formatter.format(salarioV - totalEstimado));
        banco.setText(String.format(getString(R.string.cuenta), formatter.format(salarioV - totalIngresos)) );
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
            case R.id.action_archivar_mes:
                categoriaPresenter.archivarMes();
                categoriaPresenter.obtenerGastoActual();
                return true;
            case R.id.action_ingresar_salario:
                showDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.salary));

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String salaryT = input.getText().toString();
                SharedPreferences sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat(SALARY_KEY, Float.parseFloat(salaryT));
                editor.apply();
                actualizarListaResumen();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
