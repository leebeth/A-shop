package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.GastoMesDaoImpl;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.InternalException;
import com.tienda.a_shop.model.interfaces.IGastoMesManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.utils.DateUtil;

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
    public GastoMes obtenerGastoMesActual() throws InternalException {
        GastoMes gastoMesActual = gastoMesDao.obtenerGastoMesActual();

        if (gastoMesActual == null) {
            gastoMesActual = new GastoMes(null, false, DateUtil.getNameCurrentMonth());
            app.getDaoSession().insert(gastoMesActual);
        }

        return gastoMesActual;
    }

    @Override
    void initDao() {
        gastoMesDao = new GastoMesDaoImpl(app);
    }
}
