package com.tienda.a_shop.presenters.interfaces.presenters;

/**
 * Created by Lore on 20/05/2017.
 */

public interface IItemPresenter {
    void agregarItem(long idCategoriaGastoMes, String nombre);
    void obtenerItems(long idCategoriaGastoMes);
}
