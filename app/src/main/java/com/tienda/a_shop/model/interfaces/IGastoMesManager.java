package com.tienda.a_shop.model.interfaces;

import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.InternalException;

/**
 * Created by Lore on 06/05/2017.
 * IGastoMesManager
 */

public interface IGastoMesManager {

    void agregarGastoMes(GastoMes gastoMes);
    GastoMes obtenerGastoMesActual() throws InternalException;
}
