package com.tienda.a_shop.manager;

import com.tienda.a_shop.activity.IApp;
import com.tienda.a_shop.dao.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;

/**
 * Created by Lore on 09/04/2017.
 */

public class CategoriaManager extends DefaultManager {

    private CategoriaDao categoriaDao;

    public CategoriaManager(IApp app) {
        super(app);
    }

    @Override
    void initDao() {
        categoriaDao = app.getDaoSession().getCategoriaDao();
    }

    public boolean actualizarCategoría(Categoria categoria){
        categoriaDao.update(categoria);
        return true;
    }
}
