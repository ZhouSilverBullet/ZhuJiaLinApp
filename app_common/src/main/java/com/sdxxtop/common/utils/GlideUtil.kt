package com.sdxxtop.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-17 11:36
 * Version: 1.0
 * Description:
 */
object GlideUtil {
    fun displayImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }
}