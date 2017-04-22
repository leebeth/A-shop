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

        /** //CategoriaDao.Properties.Nombre.eq(nombre)
         SQLiteDatabase db = getReadableDatabase();
         String query = "SELECT _id FROM categoria where nombre = '" + nombre + "'";
         Cursor cursor = db.rawQuery(query, null);
         int n = 0;
         while (cursor.moveToNext()) {
         n = cursor.getInt(0);
         }
         cursor.close();
         db.close();

         SQLiteDatabase db2 = getWritableDatabase();
         if (n == 0) {
         query = "INSERT INTO categoria VALUES (null, '" + nombre + "', " + estimado + ")";
         db2.execSQL(query);
         db2.close();

         //_id INTEGER, estimado INTEGER, total INTEGER, id_categoria INTEGER, id_gasto_mes INTEGER
         db2 = getWritableDatabase();
         query = "INSERT INTO categoria_gasto_mes VALUES (null, " + estimado + ", 0, " +
         "SELECT _id FROM categoria where nombre = '" + nombre + "', " + idGastoMes + ")";
         db2.execSQL(query);
         db2.close();
         } else {
         db2 = getWritableDatabase();
         query = "INSERT INTO categoria_gasto_mes VALUES (null, " + estimado + ", 0, " +
         "SELECT _id FROM categoria where nombre = '" + nombre + "', " + idGastoMes + ")";
         db2.execSQL(query);
         db2.close();
         }**/
    }

    public com.tienda.a_shop.entities.GastoMes getGastoActual() {

        com.tienda.a_shop.entities.GastoMes gasto = getGastoNoArchivado();

        if (gasto == null) {
            gasto = new com.tienda.a_shop.entities.GastoMes(null, false);
            app.getDaoSession().insert(gasto);

            gasto = getGastoNoArchivado();
        }
/**     GastoMes gastoMes = null;
 SQLiteDatabase db = getReadableDatabase();
 String query = "SELECT * FROM gasto_mes WHERE archivado = 0";
 Cursor cursor = db.rawQuery(query, null);
 if (cursor.getCount() == 0) {
 SQLiteDatabase db2 = getWritableDatabase();
 query = "INSERT INTO gasto_mes VALUES (null, 0)";
 db2.execSQL(query);
 db2.close();
 } else {
 while (cursor.moveToNext()) {
 gastoMes = new GastoMes(cursor.getInt(0), cursor.getInt(1) == 1);
 }
 }
 cursor.close();
 db.close();**/

        return gasto;
    }

    public void guardarItemGasto(String nombre, int valor, long idProducto, int totalGasto) {

        com.tienda.a_shop.entities.Item item = getItemPorNombre(nombre);

        if (item == null) {
            int total = totalGasto + valor;
            item = new com.tienda.a_shop.entities.Item(null, idProducto, nombre, totalGasto);
            app.getDaoSession().getItemDao().insert(item);

            com.tienda.a_shop.entities.CategoriaXGastoMes categoriaXGastoMes = app.getDaoSession().getCategoriaXGastoMesDao()
                    .queryBuilder().where(CategoriaXGastoMesDao.Properties.Id.eq(idProducto)).unique();

            categoriaXGastoMes.setTotal(total);

            app.getDaoSession().getCategoriaXGastoMesDao().update(categoriaXGastoMes);
        }
/**     SQLiteDatabase db = getReadableDatabase();
 String query = "SELECT _id FROM item WHERE nombre ='" + nombre + "'";
 Cursor cursor = db.rawQuery(query, null);
 int n = 0;
 while (cursor.moveToNext()) {
 n = cursor.getInt(0);
 }
 cursor.close();
 db.close();

 if (n == 0) {
 int total = totalGasto + valor;
 SQLiteDatabase db2 = getWritableDatabase();
 db2.execSQL("INSERT INTO item VALUES ( null, '" + nombre + "', " + valor + ", " + idProducto + ")");
 db2.close();

 db2 = getWritableDatabase();
 db2.execSQL("UPDATE categoria_gasto_mes SET total=" + total + " WHERE _id=" + idProducto);
 db2.close();
 } **/
    }

    public List<com.tienda.a_shop.entities.CategoriaXGastoMes> listaProductos() {
/*
        CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();
        QueryBuilder<com.tienda.a_shop.entities.CategoriaXGastoMes> qb =
                categoriaDao.queryBuilder();

        Join categoria = qb.join(com.tienda.a_shop.entities.Categoria.class, CategoriaDao.Properties.Id);
        Join gasto_mes = qb.join(com.tienda.a_shop.entities.GastoMes.class, GastoMesDao.Properties.Id);
        return  categoriaDao.loadDeepAllAndCloseCursor(qb.buildCursor().query());
        */
/*
        List<com.tienda.a_shop.entities.Categoria> categorias = app.getDaoSession().getCategoriaDao().loadAll();
        List<com.tienda.a_shop.entities.GastoMes> gastosMes = app.getDaoSession().getGastoMesDao().loadAll();
        QueryBuilder<com.tienda.a_shop.entities.CategoriaXGastoMes> categoriaXGastoMes = app.getDaoSession().getCategoriaXGastoMesDao()
                .queryBuilder();
         Join join =  categoriaXGastoMes.join(com.tienda.a_shop.entities.Item.class, ItemDao.Properties.CategoriaXGastoMesId);

        List<com.tienda.a_shop.entities.CategoriaXGastoMes> lista = categoriaXGastoMes.list();

        QueryBuilder<com.tienda.a_shop.entities.Item> itemQueryBuilder = app.getDaoSession().getItemDao().queryBuilder();
        Join join1 = itemQueryBuilder.join(com.tienda.a_shop.entities.CategoriaXGastoMes.class, CategoriaXGastoMesDao.Properties.Id);

        List<com.tienda.a_shop.entities.Item> items = itemQueryBuilder.list();

*/
        CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();
       /* QueryBuilder<com.tienda.a_shop.entities.CategoriaXGastoMes> qb =
                categoriaDao.queryBuilder();


        qb.LOG_SQL = true;
        qb.LOG_VALUES = true;

        Join categoria = qb.join(com.tienda.a_shop.entities.Categoria.class, CategoriaDao.Properties.Id);
        Join gasto_mes = qb.join(com.tienda.a_shop.entities.GastoMes.class, GastoMesDao.Properties.Id);*/
        //return  categoriaDao.loadDeepAllAndCloseCursor(qb.buildCursor().query());
        return categoriaDao.loadAll();

        /**ArrayList<CategoriaXGastoMes> result = new ArrayList<CategoriaXGastoMes>();
         SQLiteDatabase db = getReadableDatabase();
         String query = "SELECT cat.*, gasto.*, cat_gasto.* FROM categoria_gasto_mes cat_gasto " +
         "INNER JOIN categoria cat on cat._id = cat_gasto.id_categoria " +
         "INNER JOIN gasto_mes gasto on gasto._id = cat_gasto.id_gasto_mes ";
         Cursor cursor = db.rawQuery(query, null);
         while (cursor.moveToNext()) {
         Categoria categoria = new Categoria(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
         GastoMes gastoMes = new GastoMes(cursor.getInt(3), cursor.getInt(4) == 1);
         CategoriaXGastoMes categoriaXGastoMes = new CategoriaXGastoMes(cursor.getInt(5), cursor.getInt(6), categoria, gastoMes);
         result.add(categoriaXGastoMes);
         }
         cursor.close();
         db.close();**/

    }

    public List<com.tienda.a_shop.entities.Item> listaDetalleGasto(long idProducto) {
        /*ArrayList<Item> result = new ArrayList<Item>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM item WHERE id_categoria_gasto = " + idProducto, null);
        while (cursor.moveToNext()) {
            result.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        db.close();*/

        ItemDao itemDao = app.getDaoSession().getItemDao();
        return itemDao.queryBuilder().where(ItemDao.Properties.CategoriaXGastoMesId.eq(idProducto)).list();

        //return result;
    }

    public com.tienda.a_shop.entities.CategoriaXGastoMes buscarProducto(String nombre) {
        /*CategoriaXGastoMes categoriaGastoMes = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT cat.*, gasto.*, cat_gasto.* FROM categoria_gasto_mes cat_gasto" +
                "INNER JOIN categoria cat on cat._id = cat_gasto.id_categoria " +
                "INNER JOIN gasto_mes gasto on gasto._id = cat_gasto.id_gasto_mes" +
                "WHERE cat.nombre = '" + nombre + "'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Categoria categoria = new Categoria(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            GastoMes gastoMes = new GastoMes(cursor.getInt(3), cursor.getInt(4) == 1);
            categoriaGastoMes = new CategoriaXGastoMes(cursor.getInt(5), cursor.getInt(6), categoria, gastoMes);
        }
        cursor.close();
        db.close();*/

        CategoriaXGastoMesDao categoriaDao = app.getDaoSession().getCategoriaXGastoMesDao();

        QueryBuilder<com.tienda.a_shop.entities.CategoriaXGastoMes> qb =
                categoriaDao.queryBuilder();

        Join categoria = qb.join(com.tienda.a_shop.entities.Categoria.class, CategoriaDao.Properties.Id);
        Join gasto_mes = qb.join(com.tienda.a_shop.entities.GastoMes.class, GastoMesDao.Properties.Id);

        return categoriaDao.load(qb.unique().getCategoriaId());

        //return categoriaGastoMes;
    }

    public void eliminarProducto(String nombre) {
        /*SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM Productos p WHERE p.nombre = '" + nombre + "'", null);
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        db = getWritableDatabase();
        db.delete("Productos", "_id = ?", new String[]{id + ""});

        db.close();*/

        CategoriaDao categoriaDao = app.getDaoSession().getCategoriaDao();

        com.tienda.a_shop.entities.Categoria categoria =
                categoriaDao.queryBuilder().where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();

        categoriaDao.delete(categoria);

    }

    public void editarProducto(String nombreN, String nombre, int estimado) {
        /*SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Productos SET estimado=" + estimado + ", nombre ='" + nombreN + "' WHERE nombre= '" + nombre + "'");
        db.close();*/
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