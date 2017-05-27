package com.tienda.a_shop.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.dao.interfaces.CategoriaXGastoMesDao;
import com.tienda.a_shop.dao.interfaces.GastoMesDao;
import com.tienda.a_shop.dao.interfaces.ItemDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.presenters.interfaces.IApp;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Lorena on 16/10/2014.
 */
public class BDProductos {

    private IApp app;

    public BDProductos(Context context, IApp app) {
        this.app = app;
    }

    public SQLiteDatabase getReadableDatabase() {
        return null;
    }

    public SQLiteDatabase getWritableDatabase() {
        return null;
    }


     public void guardarItemGasto(String nombre, int valor, long categoriaXGastoMesId, int totalGasto) {

        Item item = getItemPorNombre(nombre);

        if (item == null) {
            int total = totalGasto + valor;
            item = new Item(null, categoriaXGastoMesId, nombre, valor);
            app.getDaoSession().getItemDao().insert(item);

            List<Item> listaItems = listaItems();

            CategoriaXGastoMes categoriaXGastoMes = app.getDaoSession().getCategoriaXGastoMesDao()
                    .queryBuilder().where(CategoriaXGastoMesDao.Properties.Id.eq(categoriaXGastoMesId)).unique();

            categoriaXGastoMes.setTotal(total);
            app.getDaoSession().getCategoriaXGastoMesDao().update(categoriaXGastoMes);
        }
    }

    public List<Item> listaItems() {
        ItemDao itemDao = app.getDaoSession().getItemDao();
        return itemDao.loadAll();

    }


    public List<Item> listaDetalleGasto(long idProducto) {
                ItemDao itemDao = app.getDaoSession().getItemDao();
        return itemDao.queryBuilder().where(ItemDao.Properties.CategoriaXGastoMesId.eq(idProducto)).list();

    }

    public Item getItemPorNombre(String nombre) {
        return app.getDaoSession().getItemDao().queryBuilder().where(ItemDao.Properties.Nombre.eq(nombre)).unique();
    }
}