package com.tienda.a_shop.dao.interfaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tienda.a_shop.entities.GastoMes;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GASTO_MES".
*/
public class GastoMesDao extends AbstractDao<GastoMes, Long> {

    public static final String TABLENAME = "GASTO_MES";

    /**
     * Properties of entity GastoMes.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Archivado = new Property(1, boolean.class, "archivado", false, "ARCHIVADO");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
    }

    private DaoSession daoSession;


    public GastoMesDao(DaoConfig config) {
        super(config);
    }
    
    public GastoMesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GASTO_MES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ARCHIVADO\" INTEGER NOT NULL ," + // 1: archivado
                "\"NAME\" TEXT);"); // 2: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GASTO_MES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GastoMes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getArchivado() ? 1L: 0L);
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GastoMes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getArchivado() ? 1L: 0L);
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
    }

    @Override
    protected final void attachEntity(GastoMes entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GastoMes readEntity(Cursor cursor, int offset) {
        GastoMes entity = new GastoMes( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getShort(offset + 1) != 0, // archivado
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GastoMes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArchivado(cursor.getShort(offset + 1) != 0);
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GastoMes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GastoMes entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GastoMes entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
