package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tienda.a_shop.R;


public class MyActivity extends Activity {

    public static final int REQUEST_TEXT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ImageView ventas = (ImageView)findViewById(R.id.ventas);
        ImageView productos = (ImageView)findViewById(R.id.productos);

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
                Intent i = new Intent( MyActivity.this, ListaCategoriasActivity.class );
                startActivity(i);
            }
        });
    }
}
