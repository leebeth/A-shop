package com.tienda.a_shop.views.interfaces;

import com.tienda.a_shop.entities.Item;

import java.util.List;

/**
 * Created by Lore on 20/05/2017.
 * ItemViewOptions
 */

public abstract class ItemViewOptions extends DefaultViewOptions {
    public void actualizarLista(List<Item> list){
        showToastLong("Not Implemented Yet :(");
    }
}
