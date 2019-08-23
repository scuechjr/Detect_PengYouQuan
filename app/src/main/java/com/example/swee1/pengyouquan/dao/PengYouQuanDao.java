package com.example.swee1.pengyouquan.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.cvte.research.faceapi.greendao.DaoMaster;
import org.cvte.research.faceapi.greendao.DaoSession;

public class PengYouQuanDao {
    private static final Object lock = new Object();
    private static final String DB_NAME = "WebChat";
    private static DaoSession mDaoSession;

    public static void initDatabase(Context context) {
        if (null == mDaoSession) {
            synchronized (lock) {
                if (null == mDaoSession) {
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(db);
                    mDaoSession = daoMaster.newSession();

                    // 打开查询的LOG
                    // QueryBuilder.LOG_SQL = true;
                    // QueryBuilder.LOG_VALUES = true;
                }
            }
        }
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
