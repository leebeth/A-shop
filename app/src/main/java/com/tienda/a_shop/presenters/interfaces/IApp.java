package com.tienda.a_shop.presenters.interfaces;

import com.tienda.a_shop.dao.interfaces.DaoSession;

/**
 * Created by Lore on 09/04/2017.
 */

public interface IApp {

    DaoSession getDaoSession();
}
