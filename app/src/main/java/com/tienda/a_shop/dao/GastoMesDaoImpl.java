package com.tienda.a_shop.dao;

import com.tienda.a_shop.dao.interfaces.GastoMesDao;
import com.tienda.a_shop.entities.Categoria;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.exceptions.InternalException;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lore on 06/05/2017.
 * GastoMesDaoImpl
 */

public class GastoMesDaoImpl {

    private GastoMesDao gastoMesDao;

    public GastoMesDaoImpl (IApp app) {
        gastoMesDao = app.getDaoSession().getGastoMesDao();
    }

    public GastoMes obtenerGastoMesActual() throws InternalException {
        try {
            GastoMes gastoMesActual = gastoMesDao.queryBuilder().where(GastoMesDao.Properties.Archivado.eq(false)).unique();
            return gastoMesActual;
        }
        catch (Exception e){
            throw new InternalException(e.getMessage(), e);
        }
    }

    public boolean archivarMes() throws InternalException {
        GastoMes gastoMesActual = obtenerGastoMesActual();
        gastoMesActual.setArchivado(true);
        gastoMesDao.update(gastoMesActual);
        return true;
    }

    public long insert(GastoMes gastoMes){
        return gastoMesDao.insert(gastoMes);
    }
}
