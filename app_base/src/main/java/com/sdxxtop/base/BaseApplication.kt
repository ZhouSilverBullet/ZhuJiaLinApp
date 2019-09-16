package com.sdxxtop.base

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.sdxxtop.common.CommonSession
import com.sdxxtop.common.receiver.ConnectivityReceiver
import leakcanary.LeakCanary
import leakcanary.LeakSentry
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

        ConnectivityReceiver.register(this)
        LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false)


    }

    abstract fun isDebug(): Boolean
}