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

    /**public void guardarProducto(String nombre, int estimado, long idGastoMes) {
        Categoria categoria = getCategoriaPorNombre(nombre);
        if (categoria == null) {
            categoria = new Categoria(null, nombre, estimado);
            app.getDaoSession().getCategoriaDao().insert(categoria);
            categoria = getCategoriaPorNombre(nombre);
            CategoriaXGastoMes categoriaGastoMes = new CategoriaXGastoMes(null, estimado, 0, categoria.getId(), idGastoMes);
            categoriaGastoMes.setCategoria(categoria);
            categoriaGastoMes.setGastoMes(app.getDaoSession().getGastoMesDao().load(idGastoMes));

            app.getDaoSession().getCategoriaXGastoMesDao().insert(categoriaGastoMes);
        }
    }**/

    public GastoMes getGastoActual() {

        GastoMes gasto = getGastoNoArchivado();

        if (gasto == null) {
            gasto = new GastoMes(null, false);
            app.getDaoSession().insert(gasto);

            gasto = getGastoNoArchivado();
        }
        return gasto;
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

    public List<CategoriaXGastoMes> listaProductos() {
        CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();
        return categoriaDao.loadAll();
            }

    public List<Item> listaDetalleGasto(long idProducto) {
                ItemDao itemDao = app.getDaoSession().getItemDao();
        return itemDao.queryBuilder().where(ItemDao.Properties.CategoriaXGastoMesId.eq(idProducto)).list();

    }

    public CategoriaXGastoMes buscarProducto(String nombre) {
          CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();

        QueryBuilder<CategoriaXGastoMes> qb =
                categoriaDao.queryBuilder();

        Join categoria = qb.join(Categoria.class, CategoriaDao.Properties.Id);
        Join gasto_mes = qb.join(GastoMes.class, GastoMesDao.Properties.Id);

        return categoriaDao.load(qb.unique().getCategoriaId());
    }

    public void eliminarProducto(String nombre) {
        CategoriaDao categoriaDao = app.getDaoSession().getCategoriaDao();

        Categoria categoria =
                categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();

        categoriaDao.delete(categoria);

    }



    public Categoria getCategoriaPorNombre(String nombre) {
        return app.getDaoSession().getCategoriaDao().queryBuilder()
                .where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
    }

    public GastoMes getGastoNoArchivado() {
        return app.getDaoSession().getGastoMesDao().queryBuilder().where(GastoMesDao.Properties.Archivado.eq(false)).unique();
    }

    public Item getItemPorNombre(String nombre) {
        return app.getDaoSession().getItemDao().queryBuilder().where(ItemDao.Properties.Nombre.eq(nombre)).unique();
    }
}