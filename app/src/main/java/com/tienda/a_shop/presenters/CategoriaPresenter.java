package com.tienda.a_shop.presenters;

import com.tienda.a_shop.model.CategoriaManager;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;
import com.tienda.a_shop.views.interfaces.DefaultViewOptions;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 * Presenter de Categoria
 */

public class CategoriaPresenter extends DefaultPresenter implements ICategoriaPresenter, IDefaultCallback<Categoria> {

    private ICategoriaManager categoriaManager;
    private DefaultViewOptions viewOptions;

    public CategoriaPresenter(IApp app, DefaultViewOptions viewOptions) {
        super(app);
        this.viewOptions = viewOptions;
    }

    @Override
    void initManager(IApp app) {
        categoriaManager = new CategoriaManager(app, this);
    }

    public void actualizarCategoría(String nombre, String nombreN, int estimado){
        Categoria categoria = new Categoria();
        categoria.setNombre(nombreN);
        categoria.setEstimado(estimado);
        categoriaManager.editarCategoria(categoria, nombre);
        //bdProductos.editarProducto(nombreN, nombre, estimado);
        //categoriaDao.update(categoria);

    }

    @Override
    public void agregarCategoria(String nombre, int estimado) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setEstimado(estimado);
        categoriaManager.agregarCategoria(categoria);
    }

    @Override
    public void onSuccess(List<Categoria> elements) {

    }

    @Override
    public void onSuccess(String message) {
        viewOptions.showToastShort(message);
    }

    @Override
    public void onError(String error) {
        viewOptions.showToastShort(error);
    }
}
