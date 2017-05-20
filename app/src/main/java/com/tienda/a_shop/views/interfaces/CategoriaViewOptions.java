package com.tienda.a_shop.views.interfaces;

import com.tienda.a_shop.entities.CategoriaXGastoMes;

import java.util.List;

/**
 * Created by Lorena on 20/05/2017.
 * CategoriaViewOptions
 */

public abstract class CategoriaViewOptions extends DefaultViewOptions{
    public void actualizarLista(List<CategoriaXGastoMes> categorias) {
        showToastLong("Not Implemented Yet :(");
    }
}
