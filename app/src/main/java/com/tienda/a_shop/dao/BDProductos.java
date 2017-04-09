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

import java.util.ArrayList;

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

    public SQLiteDatabase getReadableDatabase(){
        return null;
    }

    public SQLiteDatabase getWritableDatabase(){
        return null;
    }

    public void guardarProducto(String nombre, int estimado, int idGastoMes) {
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
        }
    }

    public GastoMes getGastoActual() {
        GastoMes gastoMes = null;
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
        db.close();

        return gastoMes;
    }

    public void guardarItemGasto(String nombre, int valor, int idProducto, int totalGasto) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id FROM item WHERE nombre ='"+nombre+"'";
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
        }
    }

    public ArrayList<CategoriaXGastoMes> listaProductos() {
        ArrayList<CategoriaXGastoMes> result = new ArrayList<CategoriaXGastoMes>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT cat.*, gasto.*, cat_gasto.* FROM categoria_gasto_mes cat_gasto " +
                "INNER JOIN categoria cat on cat._id = cat_gasto.id_categoria " +
                "INNER JOIN gasto_mes gasto on gasto._id = cat_gasto.id_gasto_mes ";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Categoria categoria = new Categoria(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            GastoMes gastoMes = new GastoMes(cursor.getInt(3),cursor.getInt(4)==1);
            CategoriaXGastoMes categoriaXGastoMes = new CategoriaXGastoMes(cursor.getInt(5), cursor.getInt(6), categoria, gastoMes);
            result.add(categoriaXGastoMes);
        }
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<Item> listaDetalleGasto(int idProducto) {
        ArrayList<Item> result = new ArrayList<Item>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM item WHERE id_categoria_gasto = " + idProducto, null);
        while (cursor.moveToNext()) {
            result.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        db.close();
        return result;
    }

    public CategoriaXGastoMes buscarProducto(String nombre) {
        CategoriaXGastoMes categoriaGastoMes = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT cat.*, gasto.*, cat_gasto.* FROM categoria_gasto_mes cat_gasto" +
                "INNER JOIN categoria cat on cat._id = cat_gasto.id_categoria " +
                "INNER JOIN gasto_mes gasto on gasto._id = cat_gasto.id_gasto_mes" +
                "WHERE cat.nombre = '"+nombre+"'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Categoria categoria = new Categoria(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            GastoMes gastoMes = new GastoMes(cursor.getInt(3),cursor.getInt(4)==1);
            categoriaGastoMes = new CategoriaXGastoMes(cursor.getInt(5), cursor.getInt(6), categoria, gastoMes);
        }
        cursor.close();
        db.close();
        return categoriaGastoMes;
    }

    public void eliminarProducto(String nombre) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM Productos p WHERE p.nombre = '" + nombre + "'", null);
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        db = getWritableDatabase();
        db.delete("Productos", "_id = ?", new String[]{id + ""});

        db.close();

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
}