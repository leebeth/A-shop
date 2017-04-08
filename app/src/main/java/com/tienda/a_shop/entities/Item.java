package com.tienda.a_shop.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lorena on 08/04/2017.
 */
@Entity
public class Item {
    @Id
    private Long id;
    private Long categoriaXGastoMesId;
    private String nombre;
    private int valor;
    @Generated(hash = 1615840858)
    public Item(Long id, Long categoriaXGastoMesId, String nombre, int valor) {
        this.id = id;
        this.categoriaXGastoMesId = categoriaXGastoMesId;
        this.nombre = nombre;
        this.valor = valor;
    }
    @Generated(hash = 1470900980)
    public Item() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCategoriaXGastoMesId() {
        return this.categoriaXGastoMesId;
    }
    public void setCategoriaXGastoMesId(Long categoriaXGastoMesId) {
        this.categoriaXGastoMesId = categoriaXGastoMesId;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getValor() {
        return this.valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
}
