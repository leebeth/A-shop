package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.DaoSession;
import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lore on 29/04/2017.
 * DefaultManager
 */

public abstract class DefaultManager {

    private IApp app;

    public DefaultManager(IApp app){
        this.app = app;
        initDao();
    }

    abstract void initDao();

    protected DaoSession getDaoSession(){
        return app.getDaoSession();
    }
}
