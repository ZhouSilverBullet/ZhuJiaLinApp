package com.sdxxtop.zjlguardian.ui.report.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:32
 * Version: 1.0
 * Description:
 */
class ReportMessageDataViewModel : BaseViewModel() {
    val mSearchShowData = MutableLiveData<Boolean>()

    fun showSearchEdit(v: View) {
//        isSearchShow.set(true)
        mSearchShowData.value = true

    }
}