package com.sdxxtop.crash.db

import com.sdxxtop.crash.data.CrashData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 15:03
 * Version: 1.0
 * Description:
 */
class CrashRealmHelper : CrashDBHelper {
    companion object {
        @JvmStatic
        val INSTANCE by lazy {
            CrashRealmHelper()
        }
        const val DB_NAME = "xxrealm.realm"
    }

    val mRealm by lazy {
        Realm.getInstance(RealmConfiguration.Builder().apply {
            deleteRealmIfMigrationNeeded()
            name(DB_NAME)
        }.build())

//        Realm.getInstance(RealmConfiguration.Builder().let {
//            it.deleteRealmIfMigrationNeeded()
//            it.name(DB_NAME)
//            it.build()
//        })

//        Realm.getInstance(RealmConfiguration.Builder().run {
//            deleteRealmIfMigrationNeeded()
//            name(DB_NAME)
//        }.build())

//        Realm.getInstance(RealmConfiguration.Builder().also {
//            it.deleteRealmIfMigrationNeeded()
//            it.name(DB_NAME)
//        }.build())
    }

    override fun queryCrashDataIsNotPush(): RealmResults<CrashData> {
        return mRealm.where(CrashData::class.java).findAll()
    }

//    fun queryCrashDataForJson(): String {
//        return mRealm.where(CrashData::class.java).findAll().asJSON()
//    }

    override fun installCrashData(crashData: CrashData) {
        mRealm.beginTransaction()
        mRealm.copyToRealm(crashData)
        mRealm.commitTransaction()
    }

    override fun deleteCrashDataIsPush() {
        mRealm.beginTransaction()
        mRealm.where(CrashData::class.java)
                .findAll()?.deleteAllFromRealm()
        mRealm.commitTransaction()
    }
}