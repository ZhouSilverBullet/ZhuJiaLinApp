package com.sdxxtop.common

import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import com.google.gson.Gson
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.common.utils.UIUtils.getResource
import es.dmoral.toasty.Toasty

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-30 15:12
 * Version: 1.0
 * Description:
 */

val GSON: Gson = Gson()

enum class ToastType {
    NONE,//不使用Toasty
    NORMAL,
    WARNING,
    INFO,
    SUCCESS,
    ERROR
}

fun Context.showToasty(msg: String?, type: ToastType = ToastType.NONE) {
    if (TextUtils.isEmpty(msg)) {
        return
    }

    when (type) {
        ToastType.NORMAL -> {
            Toasty.normal(this, msg!!)
        }

        ToastType.WARNING -> {
            Toasty.warning(this, msg!!)
        }
        ToastType.INFO -> {
            Toasty.info(this, msg!!)
        }
        ToastType.SUCCESS -> {
            Toasty.success(this, msg!!)
        }
        ToastType.ERROR -> {
            Toasty.error(this, msg!!)
        }
        else -> {
            UIUtils.showToast(msg)
        }
    }
}


/**
 * px-->dip
 */
fun View.px2dip(px: Int): Int {
    val density = getResource().getDisplayMetrics().density
    return (px / density + 0.5f).toInt()
}

/**
 * sp-->px
 */
fun View.sp2px(sp: Int): Int {
    return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), getResource().getDisplayMetrics()) + 0.5f).toInt()
}