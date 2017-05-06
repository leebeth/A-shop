package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
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

    public boolean guardarProducto(Categoria categoria, long idGastoMes) {
        Categoria categoriaEncontrada = getCategoriaPorNombre(categoria.getNombre());
        if (categoriaEncontrada == null) {
            categoriaEncontrada = new Categoria(null, categoria.getNombre(), categoria.getEstimado());
            categoriaDao.insert(categoriaEncontrada);
            categoriaEncontrada = getCategoriaPorNombre(categoria.getNombre());
            CategoriaXGastoMes categoriaGastoMes = new CategoriaXGastoMes(null, categoria.getEstimado(), 0, categoriaEncontrada.getId(), idGastoMes);
            categoriaGastoMes.setCategoria(categoriaEncontrada);
            categoriaGastoMes.setGastoMes(app.getDaoSession().getGastoMesDao().load(idGastoMes));

            app.getDaoSession().getCategoriaXGastoMesDao().insert(categoriaGastoMes);
            return true;
        }
        return false;
    }

    public Categoria getCategoriaPorNombre(String nombre) {
        return categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
    }

    public boolean editarNombreProducto(Categoria categoria, String nombre) {
        Categoria categoriaEncontrada = categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
        if (categoriaEncontrada != null) {
            categoriaEncontrada.setNombre(categoria.getNombre());
            categoriaDao.update(categoriaEncontrada);
           return true;
        }
        return false;
    }

    public boolean editarEstimadoCategoria(Categoria categoria) {
        return false;
    }
}
