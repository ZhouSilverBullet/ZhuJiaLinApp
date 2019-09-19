package com.sdxxtop.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import com.sdxxtop.common.utils.UIUtils
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import java.util.*
import kotlin.collections.ArrayList

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-19 10:19
 * Version: 1.0
 * Descriptio:
 */
class TopBanner @JvmOverloads constructor(context: Context, set: AttributeSet? = null, defStyle: Int = 0) : Banner(context, set, defStyle) {
    var tbHeight = 0f
    val banner = Banner(context)

    init {
        var a: TypedArray? = null
        try {
            a = context.theme.obtainStyledAttributes(set, R.styleable.TopBanner, defStyle, 0)
            tbHeight = a?.getDimension(R.styleable.TopBanner_tb_height, UIUtils.dip2px(130).toFloat())!!
        } finally {
            a?.recycle()
        }

        //添加banner
        addView(banner, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tbHeight.toInt()))
    }

    fun <D> setImagesAndStart(loader: GlideImageLoader<D>, images: List<D>) {
        banner.setImageLoader(loader)
        banner.setImages(images)
        banner.start()
    }

    fun testData(@DrawableRes vararg int: Int) {
        val arrayList = ArrayList<Int>()
        int.forEach {
            arrayList.add(it)
        }
        setImagesAndStart(GlideImageLoader(), arrayList)
    }

    class GlideImageLoader<D> : ImageLoaderImpl<D>() {

        override fun displayImage2(context: Context?, path: D?, imageView: ImageView?) {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.placeholder_icon) //设置“加载中”状态时显示的图片
                    .error(R.drawable.error_icon) //设置“加载失败”状态时显示的图片
            Glide.with(context!!).load(path).apply(requestOptions).into(imageView!!)

//            imageView?.setImageResource(path as Int)
//            GlideUtil.displayImage(context!!, path as String, imageView!!)
//            Glide.with(context!!).load(path.image).into(imageView!!)
//            if (path is Img) {
//                Glide.with(context!!).load(path.image).into(imageView!!)
//            }
        }
    }

    abstract class ImageLoaderImpl<T> : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            displayImage2(context, path as T, imageView)
        }

        abstract fun displayImage2(context: Context?, data: T?, imageView: ImageView?)
    }
}