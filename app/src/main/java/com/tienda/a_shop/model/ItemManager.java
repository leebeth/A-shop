package com.tienda.a_shop.model;

import android.util.Log;

import com.tienda.a_shop.dao.CategoriaGastoMesDaoImpl;
import com.tienda.a_shop.dao.ItemDaoImpl;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.model.interfaces.IItemManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Lore on 20/05/2017.
 * ItemManager
 */

public class ItemManager extends DefaultManager implements IItemManager {

    private static final String TAG = "ItemManager";

    private IDefaultCallback<Item> presenter;
    private ItemDaoImpl itemDao;
    private CategoriaGastoMesDaoImpl categoriaGastoMesDao;

    public ItemManager(IApp app, IDefaultCallback<Item> presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    public void agregarItem(Item item, int totalCategoriaGastoMes) {
        String message;
        try {
            message = app.getDaoSession().callInTx(new AgregarItemCallable(item, totalCategoriaGastoMes));
            presenter.onSuccess(message);
        }
        catch (Exception e){
            message = e.getMessage();
            Log.e(TAG, message, e);
            presenter.onError(message);
        }
    }

    @Override
    public void actualizarItem(Item item) {
        try {
            itemDao.actualizarItem(item);
            presenter.onSuccess("Item Actualizado");
        }
        catch (Exception e){
            String message = e.getMessage();
            Log.e(TAG, message, e);
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
        categoriaGastoMesDao = new CategoriaGastoMesDaoImpl(app);
    }

    private class AgregarItemCallable implements Callable<String> {

        private Item item;
        private int totalCategoriaGastoMes;

        private AgregarItemCallable(Item item, int totalCategoriaGastoMes){
            this.item = item;
            this.totalCategoriaGastoMes = totalCategoriaGastoMes;
        }

        @Override
        public String call() {
            int total = totalCategoriaGastoMes + item.getValor();
            itemDao.agregarItem(item);

            CategoriaXGastoMes categoriaXGastoMes = categoriaGastoMesDao.obtenerCategoriaGastoMes(item.getCategoriaXGastoMesId());
            categoriaXGastoMes.setTotal(total);
            categoriaGastoMesDao.actualizarCategoriaXGastoMes(categoriaXGastoMes);
            return String.format("Item %s agregado satisfactoriamente", item.getNombre());
        }
    }
}
