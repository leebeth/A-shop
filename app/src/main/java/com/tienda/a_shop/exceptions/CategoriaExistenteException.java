package com.tienda.a_shop.exceptions;

import com.tienda.a_shop.entities.Categoria;

/**
 * Created by Lore on 06/05/2017.
 * CategoriaExistenteException
 */

public class CategoriaExistenteException extends Exception {

    private Categoria categoriaEncontrada;

    public CategoriaExistenteException(String message, Categoria encontrada){
        super(message);
        this.categoriaEncontrada = encontrada;
    }

    public CategoriaExistenteException(String message){
        this(message, null);
    }

    public Categoria getCategoriaEncontrada() {
        return categoriaEncontrada;
    }
}
