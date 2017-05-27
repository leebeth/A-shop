package com.tienda.a_shop.presenters.interfaces.callbacks;

import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 * ICategoriaCallback
 */

public interface ICategoriaCallback<T> extends IDefaultCallback<T>{

    void obtenerCategoriasMesActual(List<CategoriaXGastoMes> elements) throws Exception;
    void obtenerGastoMesActual(GastoMes gastoMesActual);
}
