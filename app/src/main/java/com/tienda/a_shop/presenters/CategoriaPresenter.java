package com.tienda.a_shop.presenters;

import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.model.CategoriaManager;
import com.tienda.a_shop.model.GastoMesManager;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.model.interfaces.IGastoMesManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.presenters.interfaces.callbacks.ICategoriaCallback;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;
import com.tienda.a_shop.views.interfaces.CategoriaViewOptions;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 * Presenter de Categoria
 */

public class CategoriaPresenter extends DefaultPresenter implements ICategoriaPresenter, ICategoriaCallback<Categoria> {

    private ICategoriaManager categoriaManager;
    private IGastoMesManager gastoMesManager;
    private CategoriaViewOptions viewOptions;

    public CategoriaPresenter(IApp app, CategoriaViewOptions viewOptions) {
        super(app);
        this.viewOptions = viewOptions;
    }

    @Override
    void initManager(IApp app) {
        categoriaManager = new CategoriaManager(app, this);
        gastoMesManager = new GastoMesManager(app, this);
    }

    public void actualizarCategoría(String nombre, String nombreN, int estimado){
        Categoria categoria = new Categoria();
        categoria.setNombre(nombreN);
        categoria.setEstimado(estimado);
        categoriaManager.editarCategoria(categoria, nombre);
    }

    @Override
    public void agregarCategoria(String nombre, int estimado) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setEstimado(estimado);
        categoriaManager.agregarCategoria(categoria);
    }

    @Override
    public void eliminarCategoria(String nomCategoria) {
        categoriaManager.eliminarCategoria(nomCategoria);
    }

    @Override
    public void listarCategoriasMesActual() {
        categoriaManager.obtenerCategoriasMesActual();

    }

    @Override
    public void actualizarLista() {
        categoriaManager.obtenerCategoriasMesActual();
    }

    @Override
    public void obtenerGastoActual()  {
        categoriaManager.obtenerGastoMesActual();
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

    @Override
    public void obtenerCategoriasMesActual(List<CategoriaXGastoMes> elements) throws Exception {
        viewOptions.actualizarLista(elements);
    }

    @Override
    public void obtenerGastoMesActual(GastoMes gastoMesActual) {
        viewOptions.obtenerGastoMesActual(gastoMesActual);
    }
}
