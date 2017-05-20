package com.tienda.a_shop.presenters;

import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.model.ItemManager;
import com.tienda.a_shop.model.interfaces.IItemManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.presenters.interfaces.presenters.IItemPresenter;
import com.tienda.a_shop.views.interfaces.ItemViewOptions;

import java.util.List;

/**
 * Created by Lore on 20/05/2017.
 * ItemPresenter
 */

public class ItemPresenter extends DefaultPresenter implements IItemPresenter, IDefaultCallback<Item> {

    private IItemManager itemManager;
    private ItemViewOptions viewOptions;

    public ItemPresenter(IApp app, ItemViewOptions viewOptions) {
        super(app);
        this.viewOptions = viewOptions;
    }

    @Override
    public void agregarItem(long idCategoriaGastoMes, String nombre) {

    }

    @Override
    public void obtenerItems(long idCategoriaGastoMes) {
        itemManager.obtenerItems(idCategoriaGastoMes);
    }

    @Override
    public void onSuccess(List<Item> elements) {
        viewOptions.actualizarLista(elements);
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void obtenerCategoriasMesActual(List<CategoriaXGastoMes> elements) {

    }

    @Override
    void initManager(IApp app) {
        itemManager = new ItemManager(app, this);
    }
}
