package com.tienda.a_shop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tienda.a_shop.activity.VentaActivity;
import com.tienda.a_shop.domain.ItemGasto;
import com.tienda.a_shop.domain.Producto;

import java.util.ArrayList;

/**
 * Created by Lorena on 16/10/2014.
 */
public class BDProductos extends SQLiteOpenHelper
{
    public BDProductos(Context context)
    {
        super(context, "Productos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Creacion tabla productos
        db.execSQL("CREATE TABLE Productos (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, estimado INTEGER, total INTEGER)");
        // Creacion tabla ItemGasto
        db.execSQL("CREATE TABLE Items (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, valor INTEGER, id_producto INTEGER,"+
                " FOREIGN KEY (id_producto) REFERENCES Producto(_id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void guardarProducto(String nombre, int estimado)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT p.nombre FROM Productos p WHERE p.nombre = '" + nombre +"'", null);
        String n = null;
        while (cursor.moveToNext()){
            n = cursor.getString(0);
        }
        cursor.close();
        db.close();
        if( n == null )
        {
            SQLiteDatabase db2 = getWritableDatabase();
            db2.execSQL("INSERT INTO Productos VALUES ( null, '"+
                    nombre+"', "+estimado+", 0)");
            db2.close();
        }
    }

    public void guardarItemGasto(String nombre, int valor, int idProducto, int totalGasto)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT p.nombre FROM Items p WHERE p.nombre = '" + nombre +"'", null);
        String n = null;
        while (cursor.moveToNext()){
            n = cursor.getString(0);
        }
        cursor.close();
        db.close();
        if( n == null )
        {
            int total = totalGasto+valor;
            SQLiteDatabase db2 = getWritableDatabase();
            db2.execSQL("INSERT INTO Items VALUES ( null, '"+nombre+"', "+valor+", "+ idProducto+")");

            db2 = getWritableDatabase();
            db2.execSQL("UPDATE Productos SET total="+total+" WHERE _id="+ idProducto);
            db2.close();
        }
    }

    public ArrayList<Producto> listaProductos()
    {
        ArrayList<Producto> result = new ArrayList<Producto>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos", null);
        while (cursor.moveToNext()){
            result.add(new Producto(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3)));
        }
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<ItemGasto> listaDetalleGasto(int idProducto)
    {
        ArrayList<ItemGasto> result = new ArrayList<ItemGasto>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Items WHERE id_producto = "+ idProducto, null);
        while (cursor.moveToNext()){
            result.add(new ItemGasto(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<VentaActivity> historialDeVentas()
    {
        ArrayList<VentaActivity> result = new ArrayList<VentaActivity>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Ventas", null);
        while (cursor.moveToNext()){
            result.add(new VentaActivity(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4)));
        }
        cursor.close();
        db.close();
        return result;
    }

    public Producto buscarProducto(String nombre)
    {
        Producto p = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos p WHERE p.nombre = '" + nombre +"'", null);
        while (cursor.moveToNext()){
            p = new Producto(cursor.getInt(0), cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
        }
        cursor.close();
        db.close();
        return p;
    }

    public void eliminarProducto(String nombre)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM Productos p WHERE p.nombre = '" + nombre +"'", null);
        int id = 0;
        while (cursor.moveToNext()){
            id = cursor.getInt(0);
        }

        db = getWritableDatabase();
        db.delete("Productos",  "_id = ?", new String[]{id+""});

        db.close();

    }

    public void editarProducto(String nombreN, String nombre, int estimado)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Productos SET estimado="+estimado+", nombre ='"+nombreN+"' WHERE nombre= '"+ nombre + "'");
        db.close();
    }
}