package com.tienda.a_shop.model;

import android.util.Log;

import com.tienda.a_shop.dao.CategoriaDaoImpl;
import com.tienda.a_shop.dao.CategoriaGastoMesDaoImpl;
import com.tienda.a_shop.dao.GastoMesDaoImpl;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.CategoriaExistenteException;
import com.tienda.a_shop.exceptions.InternalException;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.ICategoriaCallback;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Lore on 29/04/2017.
 * CategoriaManager
 */

public class CategoriaManager extends DefaultManager implements ICategoriaManager {

    private static final String TAG = "CategoriaManager";

    private CategoriaDaoImpl categoriaDao;
    private CategoriaGastoMesDaoImpl categoriaGastoMesDao;
    private GastoMesDaoImpl gastoMesDao;
    private ICategoriaCallback<Categoria> presenter;

    public CategoriaManager(IApp app, ICategoriaCallback<Categoria> presenter) {
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
        String message;

        try {
            message = app.getDaoSession().callInTx(new AgregarCategoriaCallable(categoria));
            presenter.onSuccess(message);
        } catch (CategoriaExistenteException e) {
            message = e.getMessage();
            presenter.onError(message);
        }
        catch (Exception e) {
            message = "Ha ocurrido un error agregando la cateroría";
            Log.e(TAG, message, e);
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
                        throw new CategoriaExistenteException(String.format("El nombre de la Categoria %s no pudo ser editado, ya existe una Categoria %s", nombre, categoria.getNombre()));
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
        }
        catch (Exception excepcion) {
            mensaje = excepcion.getMessage();
            presenter.onError(mensaje);
        }
    }

    @Override
    public void asociarCategoriaConGastoMes(Categoria categoria, GastoMes gastoMes) throws InternalException {
        try {
            CategoriaXGastoMes categoriaGastoMes = new CategoriaXGastoMes(null, categoria.getEstimado(), 0, categoria.getId(), gastoMes.getId());
            categoriaGastoMes.setCategoria(categoria);
            categoriaGastoMes.setGastoMes(gastoMes);
            categoriaGastoMesDao.agregarCategoriaGastoMes(categoriaGastoMes);
        }
        catch (Exception e){
            throw new InternalException(e.getMessage(), e);
        }
    }

    @Override
    public void eliminarCategoria(String nomCategoria) {
        boolean eliminada;
        String mensaje;
        try{
            Categoria categoria = categoriaDao.getCategoriaPorNombre(nomCategoria);
            GastoMes gastoActual = gastoMesDao.obtenerGastoMesActual();
            eliminada = categoriaGastoMesDao.eliminarCategoriaGastoMes(categoria,gastoActual);

            if(eliminada){
                mensaje = String.format("La Categoria %s ha sido eliminada satisfactoriamente", categoria.getNombre());
                presenter.onSuccess(mensaje);
            } else {
                mensaje = String.format("La Categoria %s no pudo ser editada", categoria.getNombre());
                presenter.onError(mensaje);
            }

        }catch (Exception e)
        {
            mensaje = e.getMessage();
            presenter.onError(mensaje);
        }
    }

    @Override
    public void obtenerCategoriasMesActual() {
        try
        {
            GastoMes gastoMes = gastoMesDao.obtenerGastoMesActual();
            List<CategoriaXGastoMes> categorias = categoriaGastoMesDao.obtenerCategoriasMes(gastoMes);
            presenter.obtenerCategoriasMesActual(categorias);
        }catch(Exception e)
        {
            presenter.onError(e.getMessage());
        }
    }

    @Override
    public void obtenerGastoMesActual() {
        try {
            GastoMes gastoMesActual =gastoMesDao.obtenerGastoMesActual();
            presenter.obtenerGastoMesActual(gastoMesActual);
        }
        catch(Exception e)
        {
            presenter.onError(e.getMessage());
        }

    }

    private class AgregarCategoriaCallable implements Callable<String> {

        private Categoria categoria;

        private AgregarCategoriaCallable(Categoria categoria){
            this.categoria = categoria;
        }

        @Override
        public String call() throws CategoriaExistenteException, InternalException {
            GastoMes gastoMesActual = gastoMesDao.obtenerGastoMesActual();
            categoria = categoriaDao.guardarCategoria(categoria, gastoMesActual);
            asociarCategoriaConGastoMes(categoria, gastoMesActual);
            return String.format("Categoría %s agregada satisfactoriamente", categoria.getNombre());
        }
    }
}
