package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.CategoriaExistenteException;
import com.tienda.a_shop.exceptions.InternalException;
import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lorena on 29/04/2017.
 * CategoriaDaoImpl
 */

public class CategoriaDaoImpl {
    private final IApp app;
    private CategoriaDao categoriaDao;

    public CategoriaDaoImpl(IApp app) {
        this.app = app;
        categoriaDao = app.getDaoSession().getCategoriaDao();
    }

    public Categoria guardarCategoria(Categoria categoria, GastoMes gastoMesActual) throws CategoriaExistenteException, InternalException {
        try {
            Categoria categoriaEncontrada = getCategoriaPorNombre(categoria.getNombre());
            if (categoriaEncontrada == null) {
                long idCategoria = categoriaDao.insert(categoria);
                categoria.setId(idCategoria);
                return categoria;
            }
        }
        catch (Exception e){
            throw new InternalException(e.getMessage(), e);
        }
        throw new CategoriaExistenteException(String.format("La Categoría %s no fue agregada porque ya existía en BD", categoria.getNombre()));
    }

    public Categoria getCategoriaPorNombre(String nombre) {
        return categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
    }

    public boolean editarCategoria(Categoria categoria) {
        categoriaDao.update(categoria);
        return true;
    }
}
