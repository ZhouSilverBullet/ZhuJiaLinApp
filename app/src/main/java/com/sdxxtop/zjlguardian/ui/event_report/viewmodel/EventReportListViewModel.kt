package com.sdxxtop.zjlguardian.ui.event_report.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.event_report.data.EventReportItem

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 17:32
 * Version: 1.0
 * Description:
 */
class EventReportListViewModel : BaseViewModel() {
    val mReportList = MutableLiveData<List<EventReportItem>>()

    fun loadData(keyEventType: Int) {
        loadOnUI({
            val params = HttpParams()
            if (keyEventType == 0) {
                RetrofitClient.apiService.postEventLists(params.data)
            } else {
                RetrofitClient.apiService.postEventSelfLists(params.data)
            }
        }, {
            showLoadingDialog(false)
            mReportList.value = it.list
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}