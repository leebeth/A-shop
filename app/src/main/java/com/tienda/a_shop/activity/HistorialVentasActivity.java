package com.tienda.a_shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.BDProductos;

import java.util.ArrayList;


public class HistorialVentasActivity extends Activity {

    private ListView listaHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_ventas);

        BDProductos bd = new BDProductos(getApplicationContext());
        ArrayList<VentaActivity> ventas = bd.historialDeVentas();

        listaHistorial = (ListView)findViewById(R.id.ventas_lista);
        ArrayAdapter<VentaActivity> adapter = new ArrayAdapter<VentaActivity>(this, android.R.layout.simple_spinner_dropdown_item, ventas);
        listaHistorial.setAdapter(adapter);

        listaHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.historial_ventas, menu);
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
}
