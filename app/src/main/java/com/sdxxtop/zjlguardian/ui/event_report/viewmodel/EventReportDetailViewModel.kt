package com.sdxxtop.zjlguardian.ui.event_report.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.App
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
    //受理成功
    val mSettleSuccess = MutableLiveData<Boolean>()
    val mTransSuccess = MutableLiveData<Boolean>()
    val mFinishSuccess = MutableLiveData<Boolean>()

    var mResponseValue = ObservableField<String>()

    fun loadData(eventId: Int, path: String, requestType: Int, keyEventType: Int) {
        loadOnUI({
            val params = HttpParams()
            params.put("ei", eventId)
            params.put("ct", requestType)
            //0 事件处理 1自行处理
            RetrofitClient.apiService.postEventDetails(path, params.data)
//            if (keyEventType == TYPE_EVENT) {
//            } else {
//                RetrofitClient.apiService.postEventSelfDetails(params.data)
//            }
        }, {
            showLoadingDialog(false)
            val list = it.list

            mDetailData.value = list

        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }

    //受理
    fun eventSettle(eventId: Int) {
        showLoadingDialog(true)
        loadOnUI({
            val params = HttpParams()
            params.put("ei", eventId)
            //0 事件处理 1自行处理
            RetrofitClient.apiService.postEventSettle(params.data)
        }, {
            mSettleSuccess.value = true

            showLoadingDialog(false)
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }

    //流转
    fun eventTrans(eventId: Int) {
        showLoadingDialog(true)
        loadOnUI({
            val params = HttpParams()
            params.put("ei", eventId)
            //0 事件处理 1自行处理
            RetrofitClient.apiService.postEventTrans(params.data)
        }, {
            mTransSuccess.value = true

            showLoadingDialog(false)
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }

    //解决
    /**
     * settleType： 解决类型
     * （3：正常解决 4：无法解决）
     */
    fun eventFinish(eventId: Int, settleType: Int) {
        showLoadingDialog(true)
        val value = mResponseValue.get()
        if (value == null || value.isEmpty()) {
            when (settleType) {
                3 -> {
                    UIUtils.showToast("请填写\"解决\"回复内容")
                }
                else -> {
                    UIUtils.showToast("请填写\"无法解决\"回复内容")
                }
            }
            showLoadingDialog(false)
            return
        }
        loadOnUI({
            val params = HttpParams()
            params.put("ry", value)
            params.put("ei", eventId)
            params.put("st", settleType)
            //0 事件处理 1自行处理
            RetrofitClient.apiService.postEventFinish(params.data)
        }, {
            mFinishSuccess.value = true

            showLoadingDialog(false)
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }


}