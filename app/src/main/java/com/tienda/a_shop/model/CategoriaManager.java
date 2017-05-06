package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.CategoriaDaoImpl;
import com.tienda.a_shop.dao.CategoriaGastoMesDaoImpl;
import com.tienda.a_shop.dao.GastoMesDaoImpl;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;

/**
 * Created by Lore on 29/04/2017.
 * CategoriaManager
 */

public class CategoriaManager extends DefaultManager implements ICategoriaManager {

    private CategoriaDaoImpl categoriaDao;
    private CategoriaGastoMesDaoImpl categoriaGastoMesDao;
    private GastoMesDaoImpl gastoMesDao;
    private IDefaultCallback<Categoria> presenter;

    public CategoriaManager(IApp app, IDefaultCallback<Categoria> presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    void initDao() {
        categoriaDao = new CategoriaDaoImpl(app);
        categoriaGastoMesDao = new CategoriaGastoMesDaoImpl(app);
        gastoMesDao = new GastoMesDaoImpl(app);
    }

    @Override
    public void agregarCategoria(Categoria categoria) {
        boolean agregada;
        String message;

        try {
            GastoMes gastoMesActual = gastoMesDao.obtenerGastoMesActual();
            agregada = categoriaDao.guardarCategoria(categoria, gastoMesActual);
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
            if (categoriaEncontrada != null) {
                if (!categoria.getNombre().equals(nombre)) {
                    if(categoriaDao.getCategoriaPorNombre(categoria.getNombre()) == null) {
                        categoriaEncontrada.setNombre(categoria.getNombre());
                        editada = categoriaDao.editarCategoria(categoriaEncontrada);
                    }
                    else
                    {
                        editada = false;
                        mensaje = String.format("El nombre de la Categoria %s no pudo ser editado, ya existe una Categoria %s", nombre, categoria.getNombre());
                    }
                }
                if (categoriaEncontrada.getEstimado() != categoria.getEstimado()) {
                    categoriaEncontrada.setEstimado(categoria.getEstimado());
                    GastoMes gastoActual = gastoMesDao.obtenerGastoMesActual();
                    editada = categoriaGastoMesDao.editarEstimadoCategoria(categoriaEncontrada, gastoActual);
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
