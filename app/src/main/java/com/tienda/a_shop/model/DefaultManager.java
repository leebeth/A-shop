package com.tienda.a_shop.model;

import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lore on 29/04/2017.
 * DefaultManager
 */

abstract class DefaultManager {

    protected IApp app;

    DefaultManager(IApp app){
        this.app = app;
        initDao();
    }

    abstract void initDao();

}
