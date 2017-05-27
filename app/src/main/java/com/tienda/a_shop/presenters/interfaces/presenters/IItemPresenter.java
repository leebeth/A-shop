package com.tienda.a_shop.presenters.interfaces.presenters;

/**
 * Created by Lore on 20/05/2017.
 * IItemPresenter
 */

public interface IItemPresenter {
    void agregarItem(long idCategoriaGastoMes, String nombre, int valorItem, int totalCategoriaGastoMes);
    void obtenerItems(long idCategoriaGastoMes);
}
