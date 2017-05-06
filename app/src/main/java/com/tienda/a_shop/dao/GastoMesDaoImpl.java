package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.GastoMesDao;
import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lore on 06/05/2017.
 * GastoMesDaoImpl
 */

public class GastoMesDaoImpl {

    private GastoMesDao gastoMesDao;
    private IApp app;

    public GastoMesDaoImpl (IApp app) {
        this.app = app;
        gastoMesDao = app.getDaoSession().getGastoMesDao();
    }
}
