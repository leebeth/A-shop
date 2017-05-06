package com.tienda.a_shop.model.interfaces;

import com.tienda.a_shop.entities.Categoria;

/**
 * Created by Lore on 29/04/2017.
 * ICategoriaManager
 */

public interface ICategoriaManager {
    void agregarCategoria(Categoria categoria);
    void editarCategoria(Categoria categoria, String nombre);
}
