package com.tienda.a_shop.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lorena on 08/04/2017.
 */
@Entity
public class Categoria {

    @Id
    private Long id;
    private String nombre;
    private int estimado;

    @Generated(hash = 1733461858)
    public Categoria(Long id, String nombre, int estimado) {
        this.id = id;
        this.nombre = nombre;
        this.estimado = estimado;
    }

    @Generated(hash = 577285458)
    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
