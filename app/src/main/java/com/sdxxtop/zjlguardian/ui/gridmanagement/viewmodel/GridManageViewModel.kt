package com.sdxxtop.zjlguardian.ui.gridmanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.amap.api.mapcore.util.it
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.network.helper.HttpConstantValue
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.*

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-17 10:50
 * Version: 1.0
 * Description:
 */
class GridManageViewModel : BaseViewModel() {
    val mGridManagerData = MutableLiveData<ArrayList<GridManagerData>>()
    val mGruadeEntryData = MutableLiveData<GruadeEntry>()

    fun loadData() {
        loadOnUI({
            val params = HttpParams()
            RetrofitClient.apiService.postMainIndex(params.data)
        }, {
            showLoadingDialog(false)
//            mMessageList.value = it.list
            val list = ArrayList<GridManagerData>()
            if (it.part_event != null) {
                addMoreItem(it.part_event, list, EVENT_DEPARTMENT)
                for (gridManagerData in it.part_event) {
                    gridManagerData.eventItemType = EVENT_DEPARTMENT;
                    gridManagerData.gridType = ITEM_CELL
                    if (gridManagerData == it.part_event[it.part_event.size - 1]) {
                        gridManagerData.isLast = true
                    }
                    list.add(gridManagerData)
                }
                addLineItem(it.part_event, list)
            }

            if (it.todo_event != null) {
                addMoreItem(it.todo_event, list, EVENT_MINE)
                for (gridManagerData in it.todo_event) {
                    gridManagerData.eventItemType = EVENT_MINE;
                    gridManagerData.gridType = ITEM_CELL
                    if (gridManagerData == it.todo_event[it.todo_event.size - 1]) {
                        gridManagerData.isLast = true
                    }
                    list.add(gridManagerData)
                }
                addLineItem(it.todo_event, list)
            }

            if (it.up_event != null) {
                addMoreItem(it.up_event, list, EVENT_REPORT)

                for (gridManagerData in it.up_event) {
                    gridManagerData.eventItemType = EVENT_REPORT
                    gridManagerData.gridType = ITEM_CELL
                    if (gridManagerData == it.up_event[it.up_event.size - 1]) {
                        gridManagerData.isLast = true
                    }
                    list.add(gridManagerData)
                }

                //不为空的时候最后面加一个空白数据
                addLineItem(it.up_event, list)
            }

            mGridManagerData.value = list

            mGruadeEntryData.value = it
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }

    private fun addMoreItem(itList: List<GridManagerData>, list: ArrayList<GridManagerData>, eventType: Int) {
        //不为空的时候前面加一个更多
        if (itList.isNotEmpty()) {
            val moreItem = GridManagerData(ITEM_MORE, eventType)
            list.add(moreItem)
        }
    }

    private fun addLineItem(itList: List<GridManagerData>, list: ArrayList<GridManagerData>) {
        //不为空的时候最后面加一个空白数据
        if (itList.isNotEmpty()) {
            val moreItem = GridManagerData(ITEM_EMPTY_LINE)
            list.add(moreItem)
        }
    }
}