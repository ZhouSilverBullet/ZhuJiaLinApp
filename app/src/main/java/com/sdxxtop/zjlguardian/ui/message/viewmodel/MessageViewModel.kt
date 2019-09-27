package com.sdxxtop.zjlguardian.ui.message.viewmodel

import androidx.lifecycle.MutableLiveData
import com.amap.api.mapcore.util.it
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.event_report.data.EventReportItem
import com.sdxxtop.zjlguardian.ui.message.data.MessageItemData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-25 15:54
 * Version: 1.0
 * Description:
 */
class MessageViewModel : BaseViewModel() {
    val mMessageList = MutableLiveData<List<MessageItemData>>()

    fun loadData() {
        loadOnUI({
            val params = HttpParams()
            RetrofitClient.apiService.postMessageLists(params.data)
        }, {
            showLoadingDialog(false)
            mMessageList.value = it.list
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}