package com.tienda.a_shop.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lorena on 08/04/2017.
 */
@Entity
public class GastoMes {
    @Id
    private Long id;
    private boolean archivado;
    @Generated(hash = 1447179027)
    public GastoMes(Long id, boolean archivado) {
        this.id = id;
        this.archivado = archivado;
    }
    @Generated(hash = 518018026)
    public GastoMes() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getArchivado() {
        return this.archivado;
    }
    public void setArchivado(boolean archivado) {
        this.archivado = archivado;
    }
}
