package com.sdxxtop.trackerlibrary.db.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sdxxtop.trackerlibrary.db.DaoMaster;
import com.sdxxtop.trackerlibrary.db.TrackerPathEntryDao;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库升级用的帮助类
 */
public class UpgradeDevOpenHelper extends DaoMaster.DevOpenHelper {
    public UpgradeDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //super.onUpgrade(db, oldVersion, newVersion);

        //操作数据库的更新 有几个表升级都可以传入到下面
        MigrationHelper.getInstance().migrate(db, TrackerPathEntryDao.class);
    }

}
