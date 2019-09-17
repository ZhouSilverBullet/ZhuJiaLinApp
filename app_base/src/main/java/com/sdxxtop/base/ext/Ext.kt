package com.sdxxtop.base.ext

import android.animation.Animator
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.sdxxtop.common.utils.SystemUtil
import com.sdxxtop.common.utils.UIUtils

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 21:40
 * Version: 1.0
 * Description:
 */

fun Fragment.topViewPadding(view: View) {
    view.setPadding(0, SystemUtil.getStatusHeight(view.context), 0, 0)
//    if (isVersionMoreKitkat()) {
//    }
}

//fun isVersionMoreKitkat(): Boolean {
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//        true
//    } else {
//        false
//    }
//}

fun searchShow(v: View, editText: EditText) {
    val width = v.width
    val height = v.height
    val hypotenuse = Math.hypot(width.toDouble(), height.toDouble()).toFloat()
    val animator = ViewAnimationUtils.createCircularReveal(v, width, height, 0f, hypotenuse)
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            UIUtils.showSoftInputFromWindow(editText)
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }

    })
    v.visibility = View.VISIBLE
    animator.start()
}

fun searchGone(v: View, vBg: View) {
    val width = v.width
    val height = v.height
    val hypotenuse = Math.hypot(width.toDouble(), height.toDouble()).toFloat()
    val animator = ViewAnimationUtils.createCircularReveal(v, width, height, hypotenuse, 0f)
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            v.visibility = View.GONE
            vBg.visibility = View.GONE
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }

    })

    animator.start()
}