package com.sdxxtop.zjlguardian.ui.message.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.message.data.MDetails
import com.sdxxtop.zjlguardian.ui.message.data.MessageItemData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-25 16:20
 * Version: 1.0
 * Description:
 */
class MessageDetailViewModel : BaseViewModel() {
    val mMessageDetail = MutableLiveData<MDetails>()

    fun loadData(messageId: Int) {
        loadOnUI({
            val params = HttpParams()
            params.put("mi", messageId)
            RetrofitClient.apiService.postMessageDetails(params.data)
        }, {
            showLoadingDialog(false)
            mMessageDetail.value = it.details
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}