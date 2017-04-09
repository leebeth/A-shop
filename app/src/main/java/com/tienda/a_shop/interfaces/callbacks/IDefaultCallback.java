package com.tienda.a_shop.interfaces.callbacks;

import java.util.List;

/**
 * Created by Lore on 09/04/2017.
 */

public interface IDefaultCallback<T> {

    void onSuccess(List<T> elements);
    void onError(String error);

}
