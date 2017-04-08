package com.tienda.a_shop.domain;

/**
 * Created by Lorena on 08/04/2017.
 */public class Categoria {

    private int idCategoria;
    private String nombre;
    private int estimado;

    public Categoria(int idCategoria, String nombre, int estimado) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.estimado = estimado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstimado() {
        return estimado;
    }

    public void setEstimado(int estimado) {
        this.estimado = estimado;
    }
}
