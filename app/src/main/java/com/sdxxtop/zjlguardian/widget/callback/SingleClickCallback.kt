package com.sdxxtop.zjlguardian.widget.callback

import android.view.View

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-19 16:27
 * Version: 1.0
 * Description:
 */
interface SingleClickCallback {

    /**
     * 单次点击的回调
     */
    fun onSingleClick(v: View)
    /**
     * 点击过快的回调
     */
    fun onFastClick()
}