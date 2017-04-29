package com.tienda.a_shop.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

import com.tienda.a_shop.dao.interfaces.DaoSession;
import com.tienda.a_shop.dao.interfaces.CategoriaXGastoMesDao;
import com.tienda.a_shop.dao.interfaces.CategoriaDao;

/**
 * Created by Lorena on 08/04/2017.
 */
@Entity
public class Categoria {

    @Id
    private Long id;
    private String nombre;
    private int estimado;
    @ToMany(referencedJoinProperty = "categoriaId")
    private List<CategoriaXGastoMes> categoriaXGastoMes;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1170305099)
    private transient CategoriaDao myDao;

    @Generated(hash = 1733461858)
    public Categoria(Long id, String nombre, int estimado) {
        this.id = id;
        this.nombre = nombre;
        this.estimado = estimado;
    }

    @Generated(hash = 577285458)
    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstimado() {
        return estimado;
    }

    public void setEstimado(int estimado) {
        this.estimado = estimado;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 79620073)
    public List<CategoriaXGastoMes> getCategoriaXGastoMes() {
        if (categoriaXGastoMes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriaXGastoMesDao targetDao = daoSession.getCategoriaXGastoMesDao();
            List<CategoriaXGastoMes> categoriaXGastoMesNew = targetDao
                    ._queryCategoria_CategoriaXGastoMes(id);
            synchronized (this) {
                if (categoriaXGastoMes == null) {
                    categoriaXGastoMes = categoriaXGastoMesNew;
                }
            }
        }
        return categoriaXGastoMes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1504184150)
    public synchronized void resetCategoriaXGastoMes() {
        categoriaXGastoMes = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 829587735)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCategoriaDao() : null;
    }
}
