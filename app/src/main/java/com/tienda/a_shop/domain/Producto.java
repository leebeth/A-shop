package com.tienda.a_shop.domain;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lorena on 10/10/2014.
 */
public class Producto
{
    private int id;
    private String nombre;
    private int totalGasto;
    private int totalEstimado;
    private List<ItemGasto> items;
    private NumberFormat formatter;

    public Producto (int id, String nombre, int totalEstimado,int totalGasto)
    {
        this.id = id;
        this.nombre = nombre;
        this.totalGasto = totalGasto;
        this.totalEstimado = totalEstimado;
        items = new ArrayList<>();
        formatter=NumberFormat.getCurrencyInstance();
    }

    public Producto (int id, String nombre, int totalEstimado)
    {
        this.id = id;
        this.nombre = nombre;
        this.totalGasto = 0;
        this.totalEstimado = totalEstimado;
        items = new ArrayList<>();
        formatter=NumberFormat.getCurrencyInstance();
    }

    public int getId(){ return id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstimado() {
        return totalEstimado;
    }

    public void setEstimado(int totalEstimado) {
        this.totalEstimado = totalEstimado;
    }

    public int getTotalGasto() {
        return totalGasto;
    }

    public void setTotalGasto(int totalGasto) {
        this.totalGasto = totalGasto;
    }

    public List<ItemGasto> getItems() {
        return items;
    }

    public void setItems(List<ItemGasto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return nombre + "   " + formatter.format(totalGasto);
    }
}
