package com.tienda.a_shop.presenters.interfaces.callbacks;

import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 * IDefaultCallback
 */

public interface IDefaultCallback<T> {

    void onSuccess(List<T> elements);
    void onSuccess(String message);
    void onError(String error);
    void obtenerCategoriasMesActual(List<CategoriaXGastoMes> elements) throws Exception;

    void obtenerGastoMesActual(GastoMes gastoMesActual);
}
