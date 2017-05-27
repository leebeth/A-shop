package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.CategoriaXGastoMesDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.presenters.interfaces.IApp;

import java.util.List;

/**
 * Created by Lorena on 06/05/2017.
 * CategoriaGastoMesDaoImpl
 */

public class CategoriaGastoMesDaoImpl {
    private final IApp app;
    private CategoriaXGastoMesDao categriaGastoMesDao;

    public CategoriaGastoMesDaoImpl(IApp app) {
        this.app = app;
        categriaGastoMesDao = app.getDaoSession().getCategoriaXGastoMesDao();
    }

    public boolean editarEstimadoCategoria(Categoria categoria, GastoMes gastoActual) {
        CategoriaXGastoMes gasto = getCategoriaGastoMes(categoria,gastoActual);
        if(gasto != null) {
            gasto.setEstimado(categoria.getEstimado());
            categriaGastoMesDao.update(gasto);
            return true;
        }
        return false;
    }

    public void agregarCategoriaGastoMes(CategoriaXGastoMes categoriaXGastoMes){
        categriaGastoMesDao.insert(categoriaXGastoMes);
    }

    public boolean eliminarCategoriaGastoMes(Categoria categoria, GastoMes gastoActual) {
        CategoriaXGastoMes gastoMes = getCategoriaGastoMes(categoria,gastoActual);
        if(gastoMes != null)
        {
            categriaGastoMesDao.delete(gastoMes);
            return true;
        }
        return false;
    }

    public CategoriaXGastoMes getCategoriaGastoMes(Categoria categoria, GastoMes gastoActual)
    {
        return categriaGastoMesDao.queryBuilder().
                where(CategoriaXGastoMesDao.Properties.CategoriaId.eq(categoria.getId()),
                        CategoriaXGastoMesDao.Properties.GastoMesId.eq(gastoActual.getId())).unique();
    }

    public List<CategoriaXGastoMes> obtenerCategoriasMes(GastoMes gastoMes) {
        return categriaGastoMesDao.queryBuilder().
                where(CategoriaXGastoMesDao.Properties.GastoMesId.eq(gastoMes.getId())).list();
    }
}
