package com.king.app.salary.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.king.app.salary.conf.AppConfig;
import com.king.app.salary.model.entity.DaoMaster;
import com.king.app.salary.model.entity.DaoSession;
import com.king.app.salary.utils.DebugLog;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2018/8/29 13:50
 */
public class SalaryApplication extends Application {

    private static SalaryApplication instance;

    private DaoSession daoSession;

    private SHelper helper;
    private Database database;

    public static SalaryApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
    /**
     * 程序初始化使用外置数据库
     * 需要由外部调用，如果在onCreate里直接初始化会创建新的数据库
     */
    public void createGreenDao() {
        helper = new SHelper(this, AppConfig.DB_NAME);
        database = helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();

        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public void reCreateGreenDao() {
        daoSession.clear();
        database.close();
        helper.close();
        createGreenDao();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Database getDatabase() {
        return database;
    }

    public static class SHelper extends DaoMaster.OpenHelper {

        public SHelper(Context context, String name) {
            super(context, name);
        }

        public SHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
            DebugLog.e(" oldVersion=" + oldVersion + ", newVersion=" + newVersion);
            switch (oldVersion) {
                case 1:
                    break;
            }
        }
    }

}
