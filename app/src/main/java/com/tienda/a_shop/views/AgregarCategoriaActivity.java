package com.tienda.a_shop.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tienda.a_shop.R;
import com.tienda.a_shop.presenters.CategoriaPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;
import com.tienda.a_shop.views.interfaces.CategoriaViewOptions;

/**
 * Created by Lorena on 10/10/2014.
 * AgregarCategoriaActivity
 */
public class AgregarCategoriaActivity extends CategoriaViewOptions
{
    private EditText txtNombre;
    private EditText txtEstimado;
    private EditText txtOrden;

    private ICategoriaPresenter categoriaPresenter;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_agregar_producto);

        categoriaPresenter = new CategoriaPresenter((IApp) getApplication(), this);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtEstimado = (EditText)findViewById(R.id.txtEstimado);
        txtOrden = (EditText)findViewById(R.id.txtOrden);
        Button butAceptar = (Button)findViewById(R.id.butAceptar);
        Button butCancelar=(Button)findViewById(R.id.butCancelar);

        butAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarCategoriaActivity.this, AgregarCategoriaActivity.class);
                int estimado = txtEstimado.getText().toString().equals("")? 0 : Integer.parseInt(txtEstimado.getText().toString());
                int orden = txtOrden.getText().toString().equals("")? 0 : Integer.parseInt(txtOrden.getText().toString());
                categoriaPresenter.agregarCategoria(txtNombre.getText().toString(), estimado, orden);
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
