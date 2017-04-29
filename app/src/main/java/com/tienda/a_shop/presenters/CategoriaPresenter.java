package com.tienda.a_shop.presenters;

import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.dao.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoríaPresenter;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 * Presenter de Categoria
 */

public class CategoriaPresenter extends DefaultPresenter implements ICategoríaPresenter, IDefaultCallback<Categoria> {

    private CategoriaDao categoriaDao;

    public CategoriaPresenter(IApp app) {
        super(app);
    }

    @Override
    void initDao() {
        categoriaDao = app.getDaoSession().getCategoriaDao();
    }

    public void actualizarCategoría(String nombreN, String nombre, int estimado){


        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setEstimado(estimado);
        //bdProductos.editarProducto(nombreN, nombre, estimado);
        categoriaDao.update(categoria);
    }

    @Override
    public void onSuccess(List<Categoria> elements) {

    }

    @Override
    public void onError(String error) {

    }
}
