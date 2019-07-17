package com.tienda.a_shop.presenters;

import com.tienda.a_shop.dao.interfaces.CategoriaXGastoMesDao;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.model.CategoriaManager;
import com.tienda.a_shop.model.ItemManager;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
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
    public void agregarItem(long idCategoriaGastoMes, String nombre, int valorItem, int totalCategoriaGastoMes) {
        Item item = new Item(null, idCategoriaGastoMes, nombre, valorItem);
        itemManager.agregarItem(item, totalCategoriaGastoMes);
    }

    @Override
    public void editarItem(Item item) {
        itemManager.actualizarItem(item);
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
        viewOptions.showToastShort(message);
    }

    @Override
    public void onError(String error) {
        viewOptions.showToastShort(error);
    }

    @Override
    void initManager(IApp app) {
        itemManager = new ItemManager(app, this);
    }
}
