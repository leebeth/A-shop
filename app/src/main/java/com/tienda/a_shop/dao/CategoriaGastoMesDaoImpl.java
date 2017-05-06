package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.dao.interfaces.CategoriaXGastoMesDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lorena on 06/05/2017.
 */

public class CategoriaGastoMesDaoImpl {
    private final IApp app;
    private CategoriaXGastoMesDao categriaGastoMesDao;

    public CategoriaGastoMesDaoImpl(IApp app) {
        this.app = app;
        categriaGastoMesDao = app.getDaoSession().getCategoriaXGastoMesDao();
    }

    public boolean editarEstimadoCategoria(Categoria categoria, GastoMes gastoActual) {
        CategoriaXGastoMes gasto = categriaGastoMesDao.queryBuilder().
                where(CategoriaXGastoMesDao.Properties.CategoriaId.eq(categoria.getId()),
                CategoriaXGastoMesDao.Properties.GastoMesId.eq(gastoActual.getId())).unique();
        if(gasto != null) {
            gasto.setEstimado(categoria.getEstimado());
            categriaGastoMesDao.update(gasto);
            return true;
        }
        return false;
    }
}
