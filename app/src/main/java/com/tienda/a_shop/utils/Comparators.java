package com.tienda.a_shop.utils;

import com.tienda.a_shop.entities.CategoriaXGastoMes;

import java.util.Comparator;

public class Comparators {

    public static Comparator<CategoriaXGastoMes> getCategoryMonthComparator(){
        Comparator<CategoriaXGastoMes> comparator = new Comparator<CategoriaXGastoMes>() {
            @Override
            public int compare(CategoriaXGastoMes m1, CategoriaXGastoMes m2) {
                return m1.getCategoria().getOrden() - m2.getCategoria().getOrden();
            }
        };
        return comparator;
    }
}
