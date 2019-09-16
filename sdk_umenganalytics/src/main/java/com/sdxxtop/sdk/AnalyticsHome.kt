package com.sdxxtop.sdk

import android.content.Context
import com.umeng.analytics.MobclickAgent

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 18:34
 * Version: 1.0
 * Description:
 * 分析的 汇总类
 */
object AnalyticsHome {

    fun onActivityResume(context: Context) {
        MobclickAgent.onResume(context)
    }

    fun onActivityPause(context: Context) {
        MobclickAgent.onPause(context)
    }

    fun onFragmentResume(fragmentName: String) {
        MobclickAgent.onPageStart(fragmentName);
    }

    fun onFragmentPause(fragmentName: String) {
        MobclickAgent.onPageEnd(fragmentName);
    }

}