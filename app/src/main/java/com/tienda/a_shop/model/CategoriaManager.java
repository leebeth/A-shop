package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;

/**
 * Created by Lore on 29/04/2017.
 */

public class CategoriaManager extends DefaultManager implements ICategoriaManager{

    private CategoriaDao categoriaDao;
    private IDefaultCallback<Categoria> presenter;

    public CategoriaManager(IApp app, IDefaultCallback<Categoria> presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    void initDao() {
        categoriaDao = getDaoSession().getCategoriaDao();
    }

    @Override
    public void agregarCategoria(Categoria categoria) {
        categoriaDao.insert(categoria);
        presenter.onSuccess(String.format("Categoría %s Agregada Satisfactoriamente", categoria.getNombre()));
    }
}
