package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.ItemDao;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.presenters.interfaces.IApp;

import java.util.List;

/**
 * Created by Lore on 20/05/2017.
 * ItemDaoImpl
 */

public class ItemDaoImpl {

    private final IApp app;
    private ItemDao itemDao;

    public ItemDaoImpl(IApp app){
        this.app = app;
        itemDao = app.getDaoSession().getItemDao();
    }

    public List<Item> obtenerItems(long idCategoriaGastoMes) {
        return itemDao.queryBuilder().where(ItemDao.Properties.CategoriaXGastoMesId.eq(idCategoriaGastoMes)).list();
    }
}
