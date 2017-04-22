package com.tienda.a_shop.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.tienda.a_shop.dao.DaoSession;
import com.tienda.a_shop.dao.CategoriaXGastoMesDao;
import com.tienda.a_shop.dao.ItemDao;

/**
 * Created by Lorena on 08/04/2017.
 */
@Entity
public class Item {
    @Id
    private Long id;
    private Long categoriaXGastoMesId;
    @ToOne(joinProperty = "categoriaXGastoMesId")
    private CategoriaXGastoMes categoriaXGastoMes;
    private String nombre;
    private int valor;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 182764869)
    private transient ItemDao myDao;
    @Generated(hash = 2053355534)
    private transient Long categoriaXGastoMes__resolvedKey;
    @Generated(hash = 1615840858)
    public Item(Long id, Long categoriaXGastoMesId, String nombre, int valor) {
        this.id = id;
        this.categoriaXGastoMesId = categoriaXGastoMesId;
        this.nombre = nombre;
        this.valor = valor;
    }
    @Generated(hash = 1470900980)
    public Item() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCategoriaXGastoMesId() {
        return this.categoriaXGastoMesId;
    }
    public void setCategoriaXGastoMesId(Long categoriaXGastoMesId) {
        this.categoriaXGastoMesId = categoriaXGastoMesId;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getValor() {
        return this.valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 881601204)
    public CategoriaXGastoMes getCategoriaXGastoMes() {
        Long __key = this.categoriaXGastoMesId;
        if (categoriaXGastoMes__resolvedKey == null
                || !categoriaXGastoMes__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriaXGastoMesDao targetDao = daoSession.getCategoriaXGastoMesDao();
            CategoriaXGastoMes categoriaXGastoMesNew = targetDao.load(__key);
            synchronized (this) {
                categoriaXGastoMes = categoriaXGastoMesNew;
                categoriaXGastoMes__resolvedKey = __key;
            }
        }
        return categoriaXGastoMes;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1381098339)
    public void setCategoriaXGastoMes(CategoriaXGastoMes categoriaXGastoMes) {
        synchronized (this) {
            this.categoriaXGastoMes = categoriaXGastoMes;
            categoriaXGastoMesId = categoriaXGastoMes == null ? null
                    : categoriaXGastoMes.getId();
            categoriaXGastoMes__resolvedKey = categoriaXGastoMesId;
        }
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
    @Generated(hash = 881068859)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getItemDao() : null;
    }

    @Override
    public String toString() {
        return getNombre() + "     " + getValor();
    }
}
