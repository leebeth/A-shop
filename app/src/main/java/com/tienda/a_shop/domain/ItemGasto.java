package com.tienda.a_shop.domain;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Lorena on 14/11/2016.
 */
public class ItemGasto {
    private int id;
    private String nombre;
    private int valor;
    private int idProducto;
    private NumberFormat formatter;

    public ItemGasto(int id, String nombre, int valor) {
        formatter=NumberFormat.getCurrencyInstance();
        this.valor = valor;
        this.nombre = nombre;
        this.id = id;
    }

    public ItemGasto(int id, String nombre, int valor, int idProducto) {
        formatter=NumberFormat.getCurrencyInstance();
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.idProducto = idProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
         String valorFormatter = formatter.format(valor);
        return  nombre + "      " + valorFormatter;
    }
}
