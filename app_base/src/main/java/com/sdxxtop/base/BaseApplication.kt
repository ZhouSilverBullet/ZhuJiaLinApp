package com.sdxxtop.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import kotlin.properties.Delegates

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 18:01
 * Version: 1.0
 * Description:
 */
abstract class BaseApplication : MultiDexApplication() {
    companion object {
        @JvmStatic
        var INSTANCE: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

//        ConnectivityReceiver.register(this)
//        LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false)


    }

    abstract fun isDebug(): Boolean
}