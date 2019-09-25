package com.sdxxtop.zjlguardian.ui.commission.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.event_report.data.EventReportItem

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-25 15:00
 * Version: 1.0
 * Description:
 */
class CommissionViewModel : BaseViewModel() {
    val mReportList = MutableLiveData<List<EventReportItem>>()

    fun loadData() {
        loadOnUI({
            val params = HttpParams()
            RetrofitClient.apiService.postEventIndex(params.data)
        }, {
            showLoadingDialog(false)
            mReportList.value = it.list
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}