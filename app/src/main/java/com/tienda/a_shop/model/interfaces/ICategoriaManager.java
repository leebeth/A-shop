package com.tienda.a_shop.model.interfaces;

import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.CategoriaExistenteException;
import com.tienda.a_shop.exceptions.InternalException;

/**
 * Created by Lore on 29/04/2017.
 * ICategoriaManager
 */

public interface ICategoriaManager {
    void agregarCategoria(Categoria categoria);
    void editarCategoria(Categoria categoria, String nombre);
    void asociarCategoriaConGastoMes(Categoria categoria, GastoMes gastoMes) throws CategoriaExistenteException;
    void eliminarCategoria(String nomCategoria);
    void obtenerCategoriasMesActual();
    void obtenerGastoMesActual();
    CategoriaXGastoMes obtenerCategoriaMesActual(GastoMes mes, Categoria categoria);
    void archivarMes();
}
