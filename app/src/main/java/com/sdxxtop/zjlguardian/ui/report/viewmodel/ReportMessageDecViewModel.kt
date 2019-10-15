package com.sdxxtop.zjlguardian.ui.report.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.report.data.ReportDetailData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:32
 * Version: 1.0
 * Description:
 */
class ReportMessageDecViewModel : BaseViewModel() {

    var formId = 0

    val mReportDetail = MutableLiveData<ReportDetailData>()
    val mReportName = ObservableField<String>()
    val mDec = ObservableField<String>()
    val mEndTime = ObservableField<String>()
    val mCount = ObservableInt()
    val mTotalCount = ObservableInt()

    fun loadData() {
        loadOnUI({
            val params = HttpParams()
            params.put("fi", formId)
            RetrofitClient.apiService.postEventCollectDetails(params.data)
        }, {
            showLoadingDialog(false)
            mReportDetail.value = it.list
            if (it.list != null) {
                mReportName.set(it.list.username)
                mDec.set(it.list.desc)
                mEndTime.set(it.list.end_time)
                mCount.set(it.list.count)
                mTotalCount.set(it.list.total_count)
            }
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}