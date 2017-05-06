package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.CategoriaDaoImpl;
import com.tienda.a_shop.entities.Categoria;
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
        boolean agregada;
        String message;

        try {
            long idGastoMes = 0L; //TODO: obtener id del gasto mes actual
            agregada = categoriaDao.guardarProducto(categoria, idGastoMes);
            if (agregada) {
                message = String.format("Categoría %s agregada satisfactoriamente", categoria.getNombre());
                presenter.onSuccess(message);
            } else {
                message = String.format("La Categoría %s no fue agregada porque ya existía en BD", categoria.getNombre());
                presenter.onError(message);
            }
        } catch (Exception e) {
            message = e.getMessage();
            presenter.onError(message);
        }
    }

    @Override
    public void editarCategoria(Categoria categoria, String nombre) {
        boolean editada = false;
        String mensaje;
        try {
            Categoria categoriaEncontrada = categoriaDao.getCategoriaPorNombre(nombre);
            if (categoriaEncontrada == null) {
                if (!categoria.getNombre().equals(nombre)) {
                    editada = categoriaDao.editarNombreProducto(categoria, nombre);
                }
                if (categoriaEncontrada.getEstimado() != categoria.getEstimado()) {
                    editada = categoriaDao.editarEstimadoCategoria(categoria);
                }
            }
            if (editada) {
                mensaje = String.format("La Categoria %s ha sido editada satisfactoriamente", nombre);
                presenter.onSuccess(mensaje);
            } else {
                mensaje = String.format("La Categoria %s no pudo ser editada", nombre);
                presenter.onError(mensaje);
            }
        } catch (Exception excepcion) {
            mensaje = excepcion.getMessage();
            presenter.onError(mensaje);
        }
    }
}
