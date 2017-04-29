package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.CategoriaDaoImpl;
import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;

/**
 * Created by Lore on 29/04/2017.
 * CategoriaManager
 */

public class CategoriaManager extends DefaultManager implements ICategoriaManager {

    private CategoriaDaoImpl categoriaDao;
    private IDefaultCallback<Categoria> presenter;

    public CategoriaManager(IApp app, IDefaultCallback<Categoria> presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    void initDao() {
        categoriaDao = new CategoriaDaoImpl(app);
    }

    @Override
    public void agregarCategoria(Categoria categoria) {
        boolean agregada = false;
        String message;

        try {
            long idGastoMes = 0L; //TODO: obtener id del gasto mes actual
            agregada = categoriaDao.guardarProducto(categoria, idGastoMes);

            if (agregada)
                message = String.format("Categoría %s Agregada Satisfactoriamente", categoria.getNombre());
            else
                message = String.format("La Categoría %s No Fue Agregada Porque Ya Existía En BD", categoria.getNombre());

        } catch (Exception e)
        {
            message = e.getMessage();
        }

        if (agregada)

        {
            presenter.onSuccess(message);
        } else

        {
            presenter.onError(message);
        }

    }

    @Override
    public void editarCategoria(Categoria categoria, String nombre) {

    }

}
