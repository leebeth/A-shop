package com.tienda.a_shop.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tienda.a_shop.R;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.presenters.ItemPresenter;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.presenters.IItemPresenter;
import com.tienda.a_shop.views.interfaces.ItemViewOptions;

public class EditarItemActivity extends ItemViewOptions {

    private EditText txtNombre;
    private EditText txtValor;
    private long idProducto;
    private int totalGasto;
    private IItemPresenter itemPresenter;
    private String nombre = "";
    private int valor = 0;
    private Long id;
    private Long categoriaXGastoMesId;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_item_gasto);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtValor = (EditText)findViewById(R.id.txtValor);

        if(getIntent().getExtras() != null){
            nombre = getIntent().getExtras().getString("Nombre");
            valor = getIntent().getExtras().getInt("Valor");
            id = getIntent().getExtras().getLong("Id");
            categoriaXGastoMesId = getIntent().getExtras().getLong("CategoriaXGastoMesId");
        }
        txtNombre.setText(nombre);
        txtValor.setText(String.format("%d", valor));

        Button butAceptar = (Button)findViewById(R.id.butAceptar);
        Button butCancelar=(Button)findViewById(R.id.butCancelar);
        idProducto = getIntent().getLongExtra("idProducto", 0L);
        totalGasto = getIntent().getIntExtra("totalGasto",0);

        itemPresenter = new ItemPresenter((IApp)getApplication(),this);
        butAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                int valor = txtValor.getText().toString().equals("")?0: Integer.parseInt(txtValor.getText().toString());
                if(valor !=0)
                {
                    if( valor != (EditarItemActivity.this.valor) || !txtNombre.getText().toString().equals(nombre)){
                        Item item = new Item();
                        item.setId(id);
                        item.setCategoriaXGastoMesId(categoriaXGastoMesId);
                        item.setNombre(txtNombre.getText().toString());
                        item.setValor(valor);
                        itemPresenter.editarItem(item);
                        setResult(Activity.RESULT_OK, i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No hay cambios", Toast.LENGTH_SHORT).show();
                    }
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
