package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tienda.a_shop.R;


public class MyActivity extends Activity {

    public static final int REQUEST_TEXT = 0;
    private ImageView ventas;
    private ImageView productos;
    private ImageView promociones;
    private ImageView estadisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ventas = (ImageView)findViewById(R.id.ventas);
        productos = (ImageView)findViewById(R.id.productos);
        promociones=(ImageView)findViewById(R.id.promociones);
        estadisticas = (ImageView)findViewById(R.id.estadisticas);

        ventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent( MyActivity.this, HistorialVentasActivity.class );
                startActivity(i);
            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent( MyActivity.this, ListaProductosActivity.class );
                startActivity(i);
            }
        });
    }
}
