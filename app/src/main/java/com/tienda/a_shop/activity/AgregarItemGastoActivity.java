package com.tienda.a_shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.BDProductos;

public class AgregarItemGastoActivity extends Activity {

    private EditText txtNombre;
    private EditText txtValor;
    private Button butAceptar;
    private Button butCancelar;

    private BDProductos dbProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_item_gasto);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtValor = (EditText)findViewById(R.id.txtValor);
        butAceptar = (Button)findViewById(R.id.butAceptar);
        butCancelar=(Button)findViewById(R.id.butCancelar);
        final long idProducto = getIntent().getLongExtra("idProducto", 0L);
        final int totalGasto = getIntent().getIntExtra("totalGasto",0);

        dbProductos = new BDProductos(getApplicationContext(), (App)getApplication());
        butAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarItemGastoActivity.this, AgregarProductoActivity.class);
                int valor = txtValor.getText().toString().equals("")?0: Integer.parseInt(txtValor.getText().toString());
                if(valor !=0)
                {
                    dbProductos.guardarItemGasto(txtNombre.getText().toString(), Integer.parseInt(txtValor.getText().toString()), idProducto, totalGasto);
                    setResult(Activity.RESULT_OK, i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Agrega el valor del gasto", Toast.LENGTH_SHORT).show();
                }
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
