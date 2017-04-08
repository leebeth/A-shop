package com.tienda.a_shop.domain;

/**
 * Created by Lorena on 08/04/2017.
 */
public class Item {
    private int idItem;
    private String nombre;
    private int valor;
    private CategoriaXGastoMes categoriaXGastoMes;

    public Item(int idItem, String nombre, int valor, CategoriaXGastoMes categoriaXGastoMes) {
        this.idItem = idItem;
        this.nombre = nombre;
        this.valor = valor;
        this.categoriaXGastoMes = categoriaXGastoMes;
    }

    public Item(int idItem, String nombre, int valor) {
        this.idItem = idItem;
        this.nombre = nombre;
        this.valor = valor;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public CategoriaXGastoMes getCategoriaXGastoMes() {
        return categoriaXGastoMes;
    }

    public void setCategoriaXGastoMes(CategoriaXGastoMes categoriaXGastoMes) {
        this.categoriaXGastoMes = categoriaXGastoMes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
