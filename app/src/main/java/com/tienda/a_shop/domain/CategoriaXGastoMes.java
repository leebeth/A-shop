package com.tienda.a_shop.domain;

import java.util.List;

/**
 * Created by Lorena on 08/04/2017.
 */
public class CategoriaXGastoMes {
    private int estimado;
    private int total;
    private Categoria categoria;
    private CategoriaXGastoMes gastoMes;
    private List<Item> items;

    public CategoriaXGastoMes() {
    }

    public int getEstimado() {
        return estimado;
    }

    public void setEstimado(int estimado) {
        this.estimado = estimado;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaXGastoMes getGastoMes() {
        return gastoMes;
    }

    public void setGastoMes(CategoriaXGastoMes gastoMes) {
        this.gastoMes = gastoMes;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
