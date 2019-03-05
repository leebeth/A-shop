package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tienda.a_shop.R;

/**
 * Created by Lorena on 18/10/2014.
 * EditarProductoActivity
 */
public class EditarProductoActivity extends Activity
{
    private EditText txtNombre;
    private EditText txtEstimado;
    private EditText txtOrden;

    private String nombre;
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_agregar_producto);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtEstimado = (EditText)findViewById(R.id.txtEstimado);
        txtOrden = (EditText)findViewById(R.id.txtOrden);

        Button butAceptar = (Button)findViewById(R.id.butAceptar);
        Button butCancelar=(Button)findViewById(R.id.butCancelar);

        nombre = getIntent().getExtras().getString("nombre");
        txtNombre.setText(getIntent().getExtras().getString("nombre"));
        txtEstimado.setText(getIntent().getExtras().getString("estimado"));
        txtOrden.setText(getIntent().getExtras().getString("orden"));

           butAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent( EditarProductoActivity.this, EditarProductoActivity.class );
                i.putExtra("nombre", nombre );
                i.putExtra("nombreN", txtNombre.getText().toString() );
                i.putExtra("estimado", txtEstimado.getText().toString() );
                i.putExtra("orden", txtOrden.getText().toString() );
                setResult( Activity.RESULT_OK, i );
                EditarProductoActivity.this.finish();
            }
        });

        butCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
