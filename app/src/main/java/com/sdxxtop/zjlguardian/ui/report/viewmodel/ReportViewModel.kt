package com.sdxxtop.zjlguardian.ui.report.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.report.data.ReportItemData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 09:48
 * Version: 1.0
 * Description:
 */
class ReportViewModel : BaseViewModel() {
    val mReportList = MutableLiveData<List<ReportItemData>>()

    fun loadData() {
        loadOnUI({
            val params = HttpParams()
            RetrofitClient.apiService.postEventCollectLists(params.data)
        }, {
            showLoadingDialog(false)
            mReportList.value = it.list
        }, { code, msg, t ->
//            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}