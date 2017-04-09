package com.tienda.a_shop.manager;

import com.tienda.a_shop.interfaces.IApp;
import com.tienda.a_shop.dao.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.interfaces.callbacks.ICategoríaCallback;
import com.tienda.a_shop.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.interfaces.presenters.ICategoríaManager;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 */

public class CategoriaManager extends DefaultManager implements ICategoríaManager, IDefaultCallback<Categoria> {

    private CategoriaDao categoriaDao;

    public CategoriaManager(IApp app) {
        super(app);
    }

    @Override
    void initDao() {
        categoriaDao = app.getDaoSession().getCategoriaDao();
    }

    public void actualizarCategoría(String nombre, int estimado){
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setEstimado(estimado);

        categoriaDao.update(categoria);
    }

    @Override
    public void onSuccess(List<Categoria> elements) {

    }

    @Override
    public void onError(String error) {

    }
}
