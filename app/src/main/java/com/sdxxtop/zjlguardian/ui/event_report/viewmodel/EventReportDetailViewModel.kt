package com.sdxxtop.zjlguardian.ui.event_report.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity.Companion.TYPE_EVENT
import com.sdxxtop.zjlguardian.ui.event_report.data.DetailData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 18:10
 * Version: 1.0
 * Description:
 */
class EventReportDetailViewModel : BaseViewModel() {
    val mDetailData = MutableLiveData<DetailData>()

    fun loadData(eventId: Int, keyEventType: Int) {
        loadOnUI({
            val params = HttpParams()
            params.put("ei", eventId)
            //0 事件处理 1自行处理
            if (keyEventType == TYPE_EVENT) {
                RetrofitClient.apiService.postEventDetails(params.data)
            } else{
                RetrofitClient.apiService.postEventSelfDetails(params.data)
            }
        }, {
            showLoadingDialog(false)
            val list = it.list

            mDetailData.value = list

        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}