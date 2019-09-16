package com.sdxxtop.crash

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.sdxxtop.common.GSON
import com.sdxxtop.crash.data.CrashData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 17:04
 * Version: 1.0
 * Description:
 */
fun CrashData.toJson() = GSON.toJson(this)

/**
 * gson 自定义忽略
 */
fun CrashData.toJson2() = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }

    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        return f?.name?.equals("isPushServiceSuccess", ignoreCase = true) ?: false
    }

}).create().toJson(this)