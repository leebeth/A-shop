package com.tienda.a_shop.presenters;

import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lore on 09/04/2017.
 */

public abstract class DefaultPresenter {

    protected IApp app;

    public DefaultPresenter(IApp app){
        this.app = app;
        initDao();
    }

    abstract void initDao();
}
