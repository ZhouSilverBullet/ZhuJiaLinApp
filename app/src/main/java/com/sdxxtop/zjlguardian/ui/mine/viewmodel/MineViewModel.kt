package com.sdxxtop.zjlguardian.ui.mine.viewmodel

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.ui.TextContentView

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 19:55
 * Version: 1.0
 * Description:
 */
class MineViewModel : BaseViewModel() {
    val phone = ObservableField<String>()
    val userName = ObservableField<String>()
    val partName = ObservableField<String>()

    var aaa= "aaaaa"

    fun skipReportList(v:View) {

    }

    @BindingAdapter(value = ["tcv_content_text"], requireAll = false)
    fun setTcvConentText(view: TextContentView, value: String) {
        view.setTvContentText(value)
    }

}