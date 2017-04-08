package com.tienda.a_shop.domain;

/**
 * Created by Lorena on 08/04/2017.
 */
public class GastoMes {
    private int idGastoMes;
    private boolean archivado;

    public GastoMes(int idGastoMes, boolean archivado) {
        this.idGastoMes = idGastoMes;
        this.archivado = archivado;
    }

    public boolean isArchivado() {
        return archivado;
    }

    public void setArchivado(boolean archivado) {
        this.archivado = archivado;
    }

    public int getIdGastoMes() {
        return idGastoMes;
    }

    public void setIdGastoMes(int idGastoMes) {
        this.idGastoMes = idGastoMes;
    }
}
