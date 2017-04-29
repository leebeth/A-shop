package com.tienda.a_shop.model;

import com.tienda.a_shop.dao.CategoriaDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.model.interfaces.ICategoriaManager;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.presenters.interfaces.callbacks.IDefaultCallback;
import com.tienda.a_shop.presenters.interfaces.presenters.ICategoriaPresenter;

/**
 * Created by Lore on 29/04/2017.
 */

public class CategoriaManager extends DefaultManager implements ICategoriaManager{

    private CategoriaDao categoriaDao;
    private IDefaultCallback<Categoria> presenter;

    public CategoriaManager(IApp app, IDefaultCallback<Categoria> presenter) {
        super(app);
        this.presenter = presenter;
    }

    @Override
    void initDao() {
        categoriaDao = getDaoSession().getCategoriaDao();
    }

    @Override
    public void agregarCategoria(Categoria categoria) {
        boolean agregada = false;
        String message;

        try {
            long idGastoMes = 0L; //TODO: obtener id del gasto mes actual

            Categoria categoriaBuscada = getCategoriaPorNombre(categoria.getNombre());
            if (categoriaBuscada == null) {
                categoriaDao.insert(categoria);
                categoriaBuscada = getCategoriaPorNombre(categoria.getNombre());
                CategoriaXGastoMes categoriaGastoMes = new CategoriaXGastoMes(null, categoria.getEstimado(), 0, categoria.getId(), idGastoMes);
                categoriaGastoMes.setCategoria(categoriaBuscada);
                categoriaGastoMes.setGastoMes(getDaoSession().getGastoMesDao().load(idGastoMes));

                getDaoSession().getCategoriaXGastoMesDao().insert(categoriaGastoMes);

                agregada = true;
                message = String.format("Categoría %s Agregada Satisfactoriamente", categoria.getNombre());
            }
            else{
                message = String.format("La Categoría %s No Fue Agregada Porque Ya Existía En BD", categoria.getNombre());
            }
        }
        catch (Exception e){
            message = e.getMessage();
        }

        if(agregada){
            presenter.onSuccess(message);
        }
        else{
            presenter.onError(message);
        }
    }

    public Categoria getCategoriaPorNombre(String nombre) {
        return categoriaDao.queryBuilder()
                .where(CategoriaDao.Properties.Nombre.eq(nombre)).unique();
    }
}
