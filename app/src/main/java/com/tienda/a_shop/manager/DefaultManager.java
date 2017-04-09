package com.tienda.a_shop.manager;

import com.tienda.a_shop.interfaces.IApp;

/**
 * Created by Lore on 09/04/2017.
 */

public abstract class DefaultManager {

    protected IApp app;

    public DefaultManager(IApp app){
        this.app = app;
        initDao();
    }

    abstract void initDao();
}
