package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.presenters.interfaces.IApp;

import org.greenrobot.greendao.internal.DaoConfig;

/**
 * Created by Lorena on 29/04/2017.
 */

public class CategoriaDaoImpl {
    private final IApp app;
    private CategoriaDao categoriaDao;

    public CategoriaDaoImpl(IApp app) {
        this.app = app;
        categoriaDao = app.getDaoSession().getCategoriaDao();
    }

    public boolean guardarCategoria(Categoria categoria, GastoMes gastoMesActual) {
        Categoria categoriaEncontrada = getCategoriaPorNombre(categoria.getNombre());
        if (categoriaEncontrada == null) {
            categoriaDao.insert(categoria);
            categoriaEncontrada = getCategoriaPorNombre(categoria.getNombre());
            CategoriaXGastoMes categoriaGastoMes = new CategoriaXGastoMes(null, categoria.getEstimado(), 0, categoriaEncontrada.getId(), gastoMesActual.getId());
            categoriaGastoMes.setCategoria(categoriaEncontrada);
            categoriaGastoMes.setGastoMes(gastoMesActual);

            app.getDaoSession().getCategoriaXGastoMesDao().insert(categoriaGastoMes);
            return true;
        }
        return false;
    }

    public Categoria getCategoriaPorNombre(String nombre) {
        return categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
    }

    public boolean editarCategoria(Categoria categoria) {
        categoriaDao.update(categoria);
        return true;
    }
}
