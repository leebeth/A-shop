package com.tienda.a_shop.presenters;

import com.tienda.a_shop.presenters.interfaces.IApp;

/**
 * Created by Lore on 09/04/2017.
 * DefaultPresenter
 */

abstract class DefaultPresenter {

    DefaultPresenter(IApp app){
        initManager(app);
    }

    abstract void initManager(IApp app);
}
