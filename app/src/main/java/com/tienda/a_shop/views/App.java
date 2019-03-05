package com.tienda.a_shop.views;

import android.app.Application;
import com.tienda.a_shop.dao.interfaces.DaoMaster.DevOpenHelper;
import com.tienda.a_shop.dao.interfaces.DaoMaster;
import com.tienda.a_shop.dao.interfaces.DaoSession;
import com.tienda.a_shop.migrations.DatabaseUpgradeHelper;
import com.tienda.a_shop.presenters.interfaces.IApp;

import org.greenrobot.greendao.database.Database;


/**
 * Created by Lore on 09/04/2017.
 * App
 */

public class App extends Application implements IApp {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //Solo para desarrollo, esto borra todas las tablas cuando la version del esquema aumenta
        //DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "cashflow-db-encrypted" : "cashflow-db");
        DatabaseUpgradeHelper helper = new DatabaseUpgradeHelper(this, ENCRYPTED ? "cashflow-db-encrypted" : "cashflow-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}