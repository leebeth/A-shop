package com.tienda.a_shop.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.tienda.a_shop.dao.interfaces.DaoSession;
import com.tienda.a_shop.dao.interfaces.ItemDao;
import com.tienda.a_shop.dao.interfaces.GastoMesDao;
import com.tienda.a_shop.dao.interfaces.CategoriaDao;
import com.tienda.a_shop.dao.interfaces.CategoriaXGastoMesDao;

/**
 * Created by Lorena on 08/04/2017.
 * CategoriaXGastoMes
 */
@Entity
public class CategoriaXGastoMes {

    @Id
    private Long id;

    private double estimado;

    private double total;

    private Long categoriaId;

    private Long gastoMesId;

    @ToOne(joinProperty = "categoriaId")
    private Categoria categoria;

    @ToOne(joinProperty = "gastoMesId")
    private GastoMes gastoMes;

    @ToMany(referencedJoinProperty = "categoriaXGastoMesId")
    @OrderBy("id ASC")
    private List<Item> items;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 926528791)
    private transient CategoriaXGastoMesDao myDao;

    @Generated(hash = 1345222305)
    public CategoriaXGastoMes(Long id, double estimado, double total,
            Long categoriaId, Long gastoMesId) {
        this.id = id;
        this.estimado = estimado;
        this.total = total;
        this.categoriaId = categoriaId;
        this.gastoMesId = gastoMesId;
    }

    @Generated(hash = 1757673964)
    public CategoriaXGastoMes() {
    }

    @Generated(hash = 1426606615)
    private transient Long categoria__resolvedKey;

    @Generated(hash = 1841959907)
    private transient Long gastoMes__resolvedKey;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getEstimado() {
        return this.estimado;
    }

    public void setEstimado(double estimado) {
        this.estimado = estimado;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Long getCategoriaId() {
        return this.categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getGastoMesId() {
        return this.gastoMesId;
    }

    public void setGastoMesId(Long gastoMesId) {
        this.gastoMesId = gastoMesId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1691938245)
    public Categoria getCategoria() {
        Long __key = this.categoriaId;
        if (categoria__resolvedKey == null
                || !categoria__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriaDao targetDao = daoSession.getCategoriaDao();
            Categoria categoriaNew = targetDao.load(__key);
            synchronized (this) {
                categoria = categoriaNew;
                categoria__resolvedKey = __key;
            }
        }
        return categoria;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 633063161)
    public void setCategoria(Categoria categoria) {
        synchronized (this) {
            this.categoria = categoria;
            categoriaId = categoria == null ? null : categoria.getId();
            categoria__resolvedKey = categoriaId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 187147144)
    public GastoMes getGastoMes() {
        Long __key = this.gastoMesId;
        if (gastoMes__resolvedKey == null || !gastoMes__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GastoMesDao targetDao = daoSession.getGastoMesDao();
            GastoMes gastoMesNew = targetDao.load(__key);
            synchronized (this) {
                gastoMes = gastoMesNew;
                gastoMes__resolvedKey = __key;
            }
        }
        return gastoMes;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1668593860)
    public void setGastoMes(GastoMes gastoMes) {
        synchronized (this) {
            this.gastoMes = gastoMes;
            gastoMesId = gastoMes == null ? null : gastoMes.getId();
            gastoMes__resolvedKey = gastoMesId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1810729460)
    public List<Item> getItems() {
        if (items == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ItemDao targetDao = daoSession.getItemDao();
            List<Item> itemsNew = targetDao._queryCategoriaXGastoMes_Items(id);
            synchronized (this) {
                if (items == null) {
                    items = itemsNew;
                }
            }
        }
        return items;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1727286264)
    public synchronized void resetItems() {
        items = null;
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
    @Generated(hash = 226230319)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCategoriaXGastoMesDao() : null;
    }

    @Override
    public String toString() {
        return getCategoria().getNombre() +"     " + total;
    }
}
