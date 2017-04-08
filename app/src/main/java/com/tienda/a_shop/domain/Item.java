package com.tienda.a_shop.domain;

/**
 * Created by Lorena on 08/04/2017.
 */
public class Item {
    private int idItem;
    private CategoriaXGastoMes categoriaXGastoMes;
    private String nombre;
    private int valor;

    public Item() {
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
