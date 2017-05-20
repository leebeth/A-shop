package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.ItemDaoImpl;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.model.interfaces.IItemManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;

import java.util.List;

/**
 * Created by Lore on 20/05/2017.
 * ItemManager
 */

public class ItemManager extends DefaultManager implements IItemManager {

    private IDefaultCallback<Item> presenter;
    private ItemDaoImpl itemDao;

    public ItemManager(IApp app, IDefaultCallback<Item> presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    public void agregarItem(Item item) {
        String message;
        try {
            message = "";
            presenter.onSuccess(message);
        }
        catch (Exception e){
            message = "";
            presenter.onError(message);
        }
    }

    @Override
    public void obtenerItems(long idCategoriaGastoMes) {
        List<Item> items;
        try {
            items = itemDao.obtenerItems(idCategoriaGastoMes);
            presenter.onSuccess(items);
        }
        catch (Exception e){
            String message = "Ha ocurrido un error al cargar los items";
            presenter.onError(message);
        }
    }

    @Override
    void initDao() {
        itemDao = new ItemDaoImpl(app);
    }
}
