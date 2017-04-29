package com.tienda.a_shop.presenters.interfaces.presenters;

/**
 * Created by Lore on 09/04/2017.
 */

public interface ICategoríaPresenter {

    void actualizarCategoría(String nombreN, String nombre, int estimado);
    void agregarCategoria(String nombre, int estimado);
}
