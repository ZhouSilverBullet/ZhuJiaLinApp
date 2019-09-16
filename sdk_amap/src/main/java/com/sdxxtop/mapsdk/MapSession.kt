package com.sdxxtop.mapsdk

import android.content.Context
import java.lang.ref.WeakReference

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 14:29
 * Version: 1.0
 * Description:
 */
object MapSession {
    private lateinit var contextDef: WeakReference<Context>

    fun initAMap(context: Context) {
        this@MapSession.contextDef = WeakReference(context)
    }

    @JvmStatic
    fun getContext(): Context {
        if (contextDef.get() == null) {
            throw IllegalAccessException("AMapSession需要被初始化，用于获取资源等...")
        }
        return contextDef.get()!!
    }
}