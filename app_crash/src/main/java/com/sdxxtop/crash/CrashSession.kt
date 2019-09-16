package com.sdxxtop.crash

import android.content.Context
import com.google.gson.Gson
import com.sdxxtop.common.CommonSession
import com.sdxxtop.common.utils.AppUtils
import io.realm.Realm
import java.lang.ref.WeakReference

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 11:53
 * Version: 1.0
 * Description:
 */

object CrashSession {

    private lateinit var contextDef: WeakReference<Context>
    var DEBUG: Boolean = false
    var versionName: String = ""

    /**
     * authority -> FileProvider 中的字符串
     */
    fun initCrash(context: Context, debug: Boolean, versionName: String) {
        this@CrashSession.contextDef = WeakReference(context)
        this@CrashSession.DEBUG = debug
        this@CrashSession.versionName = versionName

        //初始化crash数据库
        Realm.init(context)
        //把handler crash初始化
        CrashHandler.getInstance().init(context)

    }

    @JvmStatic
    fun getContext(): Context {
        if (contextDef.get() == null) {
            throw IllegalAccessException("CrashSession需要被初始化，用于获取资源等...")
        }
        return contextDef.get()!!
    }

    fun getAppName() = AppUtils.getAppName(CommonSession.getContext())

    fun getPackageName() = AppUtils.getPackageName(CommonSession.getContext())
}