package com.tienda.a_shop.presenters.interfaces.presenters;

import com.tienda.a_shop.entities.CategoriaXGastoMes;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 * ICategoriaPresenter
 */

public interface ICategoriaPresenter {

    void actualizarCategoría(String nombreN, String nombre, int estimado);
    void agregarCategoria(String nombre, int estimado);
    void eliminarCategoria(String nomCategoria);

    void listarCategoriasMesActual();

    void actualizarLista();
}
