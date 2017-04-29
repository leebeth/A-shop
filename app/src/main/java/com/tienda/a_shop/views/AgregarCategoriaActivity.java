package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.BDProductos;

/**
 * Created by Lorena on 10/10/2014.
 */
public class AgregarCategoriaActivity extends Activity
{
    private EditText txtNombre;
    private EditText txtEstimado;
    private Button butAceptar;
    private Button butCancelar;

    private BDProductos dbProductos;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_agregar_producto);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtEstimado = (EditText)findViewById(R.id.txtEstimado);
        butAceptar = (Button)findViewById(R.id.butAceptar);
        butCancelar=(Button)findViewById(R.id.butCancelar);

        dbProductos = new BDProductos(getApplicationContext(), (App)getApplication());
        butAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarCategoriaActivity.this, AgregarCategoriaActivity.class);
                int estimado = txtEstimado.getText().toString().equals("")? 0 : Integer.parseInt(txtEstimado.getText().toString());
                dbProductos.guardarProducto(txtNombre.getText().toString(),estimado, getIntent().getLongExtra("idGastoMes",0L)  );
                setResult(Activity.RESULT_OK, i);
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
