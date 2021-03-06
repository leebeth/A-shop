package com.tienda.a_shop.model.interfaces;

import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;

/**
 * Created by Lore on 20/05/2017.
 * IItemManager
 */

public interface IItemManager {
    void agregarItem(Item item);
    void actualizarItem(Item item);
    void obtenerItems(long idCategoriaGastoMes);

    void eliminarItem(Item item);
}
