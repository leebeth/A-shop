package com.tienda.a_shop.views;

import android.app.Application;

import com.tienda.a_shop.dao.DaoSession;
import com.tienda.a_shop.dao.DaoMaster;
import com.tienda.a_shop.dao.DaoMaster.DevOpenHelper;
import com.tienda.a_shop.presenters.interfaces.IApp;

import org.greenrobot.greendao.database.Database;


/**
 * Created by Lore on 09/04/2017.
 */

public class App extends Application implements IApp {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "cashflow-db-encrypted" : "cashflow-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}