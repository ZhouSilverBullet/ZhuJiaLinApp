package com.sdxxtop.common.utils

import com.orhanobut.logger.Logger

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 16:26
 * Version: 1.0
 * Description:
 */

object LogUtil {

    const val TAG = "com.sdxxtop.common"

    @JvmStatic
    fun e(tag: String, o: Any) {
        Logger.e(tag, o)
    }

    @JvmStatic
    fun e(o: Any?) {
        e(TAG, o ?: "null")
    }

    @JvmStatic
    fun w(tag: String, o: Any) {
        Logger.w(tag, o)
    }

    @JvmStatic
    fun w(o: Any?) {
        w(TAG, o ?: "null")
    }

    @JvmStatic
    fun d(msg: String) {
        Logger.d(msg)
    }

    @JvmStatic
    fun i(msg: String) {
        Logger.i(msg)
    }
}