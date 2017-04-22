package com.tienda.a_shop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tienda.a_shop.activity.VentaActivity;
import com.tienda.a_shop.domain.Categoria;
import com.tienda.a_shop.domain.CategoriaXGastoMes;
import com.tienda.a_shop.domain.GastoMes;
import com.tienda.a_shop.domain.Item;
import com.tienda.a_shop.interfaces.IApp;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lorena on 16/10/2014.
 */
public class BDProductos /*extends SQLiteOpenHelper*/ {

    private IApp app;

    public BDProductos(Context context, IApp app) {
        //super(context, "Productos", null, 1);
        this.app = app;
    }
/*
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creacion tabla Categoria
        db.execSQL("CREATE TABLE categoria (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, estimado INTEGER)");

        //Creación tabla Gasto Mes
        db.execSQL("CREATE TABLE gasto_mes (_id INTEGER PRIMARY KEY AUTOINCREMENT, archivado INTEGER)");

        //Creación tabla Categoria x Gasto Mes
        db.execSQL("CREATE TABLE categoria_gasto_mes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, estimado INTEGER, total INTEGER, id_categoria INTEGER, id_gasto_mes INTEGER, " +
                "FOREIGN KEY (id_categoria) REFERENCES categoria(_id), " +
                "FOREIGN KEY (id_gasto_mes) REFERENCES gasto_mes(_id))");

        // Creacion tabla Item
        db.execSQL("CREATE TABLE item (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, valor INTEGER, id_categoria_gasto INTEGER, " +
                "FOREIGN KEY (id_categoria_gasto) REFERENCES Producto(_id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }*/

    public SQLiteDatabase getReadableDatabase() {
        return null;
    }

    public SQLiteDatabase getWritableDatabase() {
        return null;
    }

    public void guardarProducto(String nombre, int estimado, long idGastoMes) {
        com.tienda.a_shop.entities.Categoria categoria = getCategoriaPorNombre(nombre);
        if (categoria == null) {
            categoria = new com.tienda.a_shop.entities.Categoria(null, nombre, estimado);
            app.getDaoSession().getCategoriaDao().insert(categoria);
            categoria = getCategoriaPorNombre(nombre);
            com.tienda.a_shop.entities.CategoriaXGastoMes categoriaGastoMes = new com.tienda.a_shop.entities.CategoriaXGastoMes(null, estimado, 0, categoria.getId(), idGastoMes);
            categoriaGastoMes.setCategoria(categoria);
            categoriaGastoMes.setGastoMes(app.getDaoSession().getGastoMesDao().load(idGastoMes));

            app.getDaoSession().getCategoriaXGastoMesDao().insert(categoriaGastoMes);
        }
    }

    public com.tienda.a_shop.entities.GastoMes getGastoActual() {

        com.tienda.a_shop.entities.GastoMes gasto = getGastoNoArchivado();

        if (gasto == null) {
            gasto = new com.tienda.a_shop.entities.GastoMes(null, false);
            app.getDaoSession().insert(gasto);

            gasto = getGastoNoArchivado();
        }
        return gasto;
    }

    public void guardarItemGasto(String nombre, int valor, long categoriaXGastoMesId, int totalGasto) {

        com.tienda.a_shop.entities.Item item = getItemPorNombre(nombre);

        if (item == null) {
            int total = totalGasto + valor;
            item = new com.tienda.a_shop.entities.Item(null, categoriaXGastoMesId, nombre, valor);
            app.getDaoSession().getItemDao().insert(item);

            List<com.tienda.a_shop.entities.Item> listaItems = listaItems();

            com.tienda.a_shop.entities.CategoriaXGastoMes categoriaXGastoMes = app.getDaoSession().getCategoriaXGastoMesDao()
                    .queryBuilder().where(CategoriaXGastoMesDao.Properties.Id.eq(categoriaXGastoMesId)).unique();

            categoriaXGastoMes.setTotal(total);
            app.getDaoSession().getCategoriaXGastoMesDao().update(categoriaXGastoMes);
        }
    }

    public List<com.tienda.a_shop.entities.Item> listaItems() {
        ItemDao itemDao = app.getDaoSession().getItemDao();
        return itemDao.loadAll();

    }

    public List<com.tienda.a_shop.entities.CategoriaXGastoMes> listaProductos() {
        CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();
        return categoriaDao.loadAll();
            }

    public List<com.tienda.a_shop.entities.Item> listaDetalleGasto(long idProducto) {
                ItemDao itemDao = app.getDaoSession().getItemDao();
        return itemDao.queryBuilder().where(ItemDao.Properties.CategoriaXGastoMesId.eq(idProducto)).list();

    }

    public com.tienda.a_shop.entities.CategoriaXGastoMes buscarProducto(String nombre) {
          CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();

        QueryBuilder<com.tienda.a_shop.entities.CategoriaXGastoMes> qb =
                categoriaDao.queryBuilder();

        Join categoria = qb.join(com.tienda.a_shop.entities.Categoria.class, CategoriaDao.Properties.Id);
        Join gasto_mes = qb.join(com.tienda.a_shop.entities.GastoMes.class, GastoMesDao.Properties.Id);

        return categoriaDao.load(qb.unique().getCategoriaId());
    }

    public void eliminarProducto(String nombre) {
        CategoriaDao categoriaDao = app.getDaoSession().getCategoriaDao();

        com.tienda.a_shop.entities.Categoria categoria =
                categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();

        categoriaDao.delete(categoria);

    }

    public void editarProducto(String nombreN, String nombre, int estimado) {
          CategoriaDao categoriaDao = app.getDaoSession().getCategoriaDao();

        com.tienda.a_shop.entities.Categoria categoria =
                categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();

        categoria.setNombre(nombreN);
        categoria.setEstimado(estimado);

        categoriaDao.update(categoria);
    }

    public com.tienda.a_shop.entities.Categoria getCategoriaPorNombre(String nombre) {
        return app.getDaoSession().getCategoriaDao().queryBuilder()
                .where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
    }

    public com.tienda.a_shop.entities.GastoMes getGastoNoArchivado() {
        return app.getDaoSession().getGastoMesDao().queryBuilder().where(GastoMesDao.Properties.Archivado.eq(false)).unique();
    }

    public com.tienda.a_shop.entities.Item getItemPorNombre(String nombre) {
        return app.getDaoSession().getItemDao().queryBuilder().where(ItemDao.Properties.Nombre.eq(nombre)).unique();
    }
}