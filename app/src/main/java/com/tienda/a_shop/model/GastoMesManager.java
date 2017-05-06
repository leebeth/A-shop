package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.GastoMesDaoImpl;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.model.interfaces.IGastoMesManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;

/**
 * Created by Lore on 06/05/2017.
 * GastoMesManager
 */

public class GastoMesManager extends DefaultManager implements IGastoMesManager {

    private GastoMesDaoImpl gastoMesDao;
    private IDefaultCallback presenter;

    public GastoMesManager(IApp app, IDefaultCallback presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    public void agregarGastoMes(GastoMes gastoMes) {

    }

    @Override
    public GastoMes obtenerGastoMesActual() {
        GastoMes gastoMesActual = gastoMesDao.obtenerGastoMesActual();

        if (gastoMesActual == null) {
            gastoMesActual = new GastoMes(null, false);
            app.getDaoSession().insert(gastoMesActual);
        }

        return gastoMesActual;
    }

    @Override
    void initDao() {
        gastoMesDao = new GastoMesDaoImpl(app);
    }
}
