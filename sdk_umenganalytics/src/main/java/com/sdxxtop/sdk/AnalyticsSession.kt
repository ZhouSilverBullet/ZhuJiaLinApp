package com.sdxxtop.sdk

import android.content.Context
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 18:34
 * Version: 1.0
 * Description:
 */

object AnalyticsSession {
    /**
     *
     */
    fun initAnalytics(applicationContext: Context, isDebug: Boolean, appKey: String, channel: String = "Umeng", deviceType: Int = UMConfigure.DEVICE_TYPE_PHONE, pushSecret: String? = null) {
        initYouMeng(applicationContext, isDebug, appKey, channel, deviceType, pushSecret)
        if (isDebug) {
            // 关闭debug错误统计
            MobclickAgent.setCatchUncaughtExceptions(false)
        }
    }

    private fun initYouMeng(applicationContext: Context, isDebug: Boolean, appKey: String, channel: String, deviceType: Int, pushSecret: String?) {
        /*******  初始化友盟   */
        UMConfigure.setLogEnabled(isDebug)
//        UMConfigure.init(applicationContext, "5be16fa8b465f5b0cf00030e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null)
        UMConfigure.init(applicationContext, appKey, channel, deviceType, pushSecret)
    }
}