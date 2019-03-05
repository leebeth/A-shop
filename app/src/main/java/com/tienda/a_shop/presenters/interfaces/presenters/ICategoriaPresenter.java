package com.tienda.a_shop.presenters.interfaces.presenters;

/**
 * Created by Lore on 09/04/2017.
 * ICategoriaPresenter
 */

public interface ICategoriaPresenter {

    void actualizarCategoría(String nombreN, String nombre, int estimado, int orden);
    void agregarCategoria(String nombre, int estimado, int orden);
    void eliminarCategoria(String nomCategoria);
    void listarCategoriasMesActual();
    void actualizarLista();
    void obtenerGastoActual();
    void archivarMes();
}
