package com.tienda.a_shop.model.interfaces;

import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.InternalException;

/**
 * Created by Lore on 29/04/2017.
 * ICategoriaManager
 */

public interface ICategoriaManager {
    void agregarCategoria(Categoria categoria);
    void editarCategoria(Categoria categoria, String nombre);
    void asociarCategoriaConGastoMes(Categoria categoria, GastoMes gastoMes) throws InternalException;

    void eliminarCategoria(String nomCategoria);

    void obtenerCategoriasMesActual();
}
