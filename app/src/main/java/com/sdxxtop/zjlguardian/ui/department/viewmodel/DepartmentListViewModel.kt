package com.sdxxtop.zjlguardian.ui.department.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.department.data.DepartmentDataItem

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-10-08 15:22
 * Version: 1.0
 * Description:
 */
class DepartmentListViewModel : BaseViewModel() {
    val mDepartmentList = MutableLiveData<List<DepartmentDataItem>>()

    fun loadData(statusType: Int, classificationType: Int, sortType: Int) {
        loadOnUI({
            val params = HttpParams()

            if (statusType != 0) {
                params.put("ss", statusType)
            }
            if (classificationType != 0) {
                params.put("ct", classificationType)
            }

            if (sortType != 0) {
                params.put("or", sortType)
            }

            RetrofitClient.apiService.postEventPartLists(params.data)
        }, {
            showLoadingDialog(false)
            mDepartmentList.value = it.list
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}