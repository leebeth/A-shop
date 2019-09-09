package com.tienda.a_shop.utils;

import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;

public class CategoriaGastoMesUtils {

    public static double getTotal(CategoriaXGastoMes cat){
        double total = 0;

        for (Item item: cat.getItems()) {
            total += item.getValor();
        }
        return total;
    }
}
