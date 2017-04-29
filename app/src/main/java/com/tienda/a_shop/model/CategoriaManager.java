package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.CategoriaDao;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.views.interfaces.DefaultViewOptions;

/**
 * Created by Lore on 29/04/2017.
 */

public class CategoriaManager extends DefaultManager{

    private CategoriaDao categoriaDao;

    public CategoriaManager(IApp app) {
        super(app);
    }

    @Override
    void initDao() {
        categoriaDao = getDaoSession().getCategoriaDao();
    }


}
