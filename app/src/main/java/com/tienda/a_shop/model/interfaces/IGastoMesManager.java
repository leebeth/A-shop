package com.tienda.a_shop.model.interfaces;

import com.tienda.a_shop.entities.GastoMes;

/**
 * Created by Lore on 06/05/2017.
 * IGastoMesManager
 */

public interface IGastoMesManager {

    void agregarGastoMes(GastoMes gastoMes);
    void obtenerGastoMesActual();
}
