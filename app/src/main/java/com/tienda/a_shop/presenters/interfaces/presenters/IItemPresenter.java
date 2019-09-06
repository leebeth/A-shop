package com.tienda.a_shop.presenters.interfaces.presenters;

import com.tienda.a_shop.entities.Item;

/**
 * Created by Lore on 20/05/2017.
 * IItemPresenter
 */

public interface IItemPresenter {
    void agregarItem(long idCategoriaGastoMes, String nombre, int valorItem, int totalCategoriaGastoMes);
    void editarItem(Item item);
    void obtenerItems(long idCategoriaGastoMes);

    void eliminarItem(Item item);
}
