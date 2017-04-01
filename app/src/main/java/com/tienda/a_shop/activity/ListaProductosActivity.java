package com.tienda.a_shop.activity;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.BDProductos;
import com.tienda.a_shop.domain.Producto;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Lorena on 10/10/2014.
 */
public class ListaProductosActivity extends Activity
{
    public static final int REQUEST_TEXT = 0;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_DETAIL = 2;
    private ListView listaProductos;
    private ArrayList<Producto> productos;
    private ImageView agregarProducto;
    private BDProductos bdProductos;
    private TextView gasto;
    private TextView ingreso;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bdProductos = new BDProductos(getApplicationContext());
        listaProductos = (ListView)findViewById(R.id.listaProductos);
        gasto = (TextView)findViewById(R.id.txtTotalGastos);
        ingreso = (TextView)findViewById(R.id.txtTotalIngreso);
        total = (TextView)findViewById(R.id.txtTotal);

        productos =  bdProductos.listaProductos();
        ArrayAdapter<Producto> adapter = new ArrayAdapter<Producto>(this, android.R.layout.simple_spinner_dropdown_item, productos);
        listaProductos.setAdapter(adapter);

        if(productos.isEmpty())
        {
           bdProductos.guardarProducto("Ingresos",0);
        }

        actualizarListaResumen();

        registerForContextMenu(listaProductos);
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent i = new Intent( ListaProductosActivity.this, ListaItemsProductosActivity.class );
                i.putExtra("idProducto", productos.get(position).getId());
                i.putExtra("nombreProducto", productos.get(position).getNombre());
                i.putExtra("estimadoProducto", productos.get(position).getEstimado());
                startActivityForResult(i, REQUEST_DETAIL);
            }
        });

        //agregar producto
        agregarProducto = (ImageView)findViewById(R.id.agregarProducto);
        agregarProducto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                Intent i = new Intent( ListaProductosActivity.this, AgregarProductoActivity.class );
                startActivityForResult(i, REQUEST_ADD);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.agregar_producto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.action_editar_producto:
                Intent i = new Intent(ListaProductosActivity.this, EditarProductoActivity.class);
                Producto p = productos.get(info.position);
                i.putExtra("nombre", p.getNombre() );
                i.putExtra("estimado", p.getEstimado()+"");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agregar_producto, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_TEXT ){
            if ( resultCode == Activity.RESULT_OK ){
                String nombre = data.getExtras().getString("nombre");
                String nombreN = data.getExtras().getString("nombreN").toString();
                int estimado = Integer.parseInt(data.getExtras().getString("estimado").toString());

                editarProducto(nombreN, nombre, estimado);
            }
        }
        if(requestCode == REQUEST_ADD)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                actualizarLista();
            }
        }
        if(requestCode == REQUEST_DETAIL)
        {
            actualizarLista();
        }
    }

    private void editarProducto(String nombreN, String nombre, int estimado) {
        bdProductos.editarProducto(nombreN, nombre, estimado);
        actualizarLista();
    }

    public void eliminarProducto(int posicion)
    {
        String nomP =productos.get(posicion).getNombre();
        bdProductos.eliminarProducto(nomP);
        actualizarLista();
    }

    public void actualizarLista()
    {
        productos =  bdProductos.listaProductos();
        ArrayAdapter<Producto> adapter = new ArrayAdapter<Producto>(this, android.R.layout.simple_spinner_dropdown_item, productos);
        listaProductos.setAdapter(adapter);
        actualizarListaResumen();
    }

    private void actualizarListaResumen()
    {
        int totalAux = 0;
        for (int i =1; i< productos.size(); i++){
            totalAux += productos.get(i).getTotalGasto();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        gasto.setText(getString(R.string.gastos) + formatter.format(totalAux));
        ingreso.setText(getString(R.string.ingresos)+formatter.format(productos.get(0).getTotalGasto()));
        total.setText(getString(R.string.total)+formatter.format(productos.get(0).getTotalGasto()-totalAux));
    }
}
