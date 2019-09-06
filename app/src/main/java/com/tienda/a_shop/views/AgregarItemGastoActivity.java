package com.tienda.a_shop.views;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tienda.a_shop.R;
import com.tienda.a_shop.presenters.ItemPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.IItemPresenter;
import com.tienda.a_shop.views.interfaces.ItemViewOptions;

public class AgregarItemGastoActivity extends ItemViewOptions {

    private EditText txtNombre;
    private EditText txtValor;
    private long idProducto;
    private int totalGasto;
    private IItemPresenter itemPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_item_gasto);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtValor = (EditText)findViewById(R.id.txtValor);
        Button butAceptar = (Button)findViewById(R.id.butAceptar);
        Button butCancelar=(Button)findViewById(R.id.butCancelar);
        idProducto = getIntent().getLongExtra("idProducto", 0L);
        totalGasto = getIntent().getIntExtra("totalGasto",0);
        String nombreProducto = getIntent().getStringExtra("nombreProducto");
        final long idIngresos = getIntent().getLongExtra("idIngresos", 0L);
        ((TextView) findViewById(R.id.label_agregar_producto)).setText(nombreProducto);

        final Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        simpleSwitch.setVisibility( idProducto == idIngresos ? View.INVISIBLE : View.VISIBLE );

        itemPresenter = new ItemPresenter((IApp)getApplication(),this);
        butAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarItemGastoActivity.this, AgregarCategoriaActivity.class);
                int valor = txtValor.getText().toString().equals("")?0: Integer.parseInt(txtValor.getText().toString());
                if(valor !=0)
                {
                    int valorGastoActual = Integer.parseInt(txtValor.getText().toString());
                    itemPresenter.agregarItem(idProducto, txtNombre.getText().toString(),valorGastoActual, totalGasto);
                    totalGasto += valorGastoActual;

                    boolean switchState = simpleSwitch.isChecked();
                    if(switchState){
                        int totalIngresos = getIntent().getIntExtra("totalIngresos",0);
                        itemPresenter.agregarItem(idIngresos, txtNombre.getText().toString(),valorGastoActual, totalIngresos);
                    }
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
