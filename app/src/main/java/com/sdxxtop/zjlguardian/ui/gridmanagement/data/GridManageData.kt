package com.sdxxtop.zjlguardian.ui.gridmanagement.data

import androidx.annotation.Keep
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-17 15:02
 * Version: 1.0
 * Description:
 */

/**
 * 更多item
 */
const val ITEM_MORE = 0
/**
 * 内容
 */
const val ITEM_CELL = 1
/**
 * 空白的16dp区域
 */
const val ITEM_EMPTY_LINE = 2

/**
 * 1.事件部门
 */
const val EVENT_DEPARTMENT = 1
/**
 * 2.我的代办
 */
const val EVENT_MINE = 2
/**
 * 空3.我的上报
 */
const val EVENT_REPORT = 3


@Keep
class GridManagerData(var gridType: Int = ITEM_MORE,
        //1.事件部门 2.我的代办 3.我的上报
                      var eventItemType: Int = EVENT_DEPARTMENT,
                      var isLast:Boolean = false) : MultiItemEntity {
    var add_date: String? = null
    var cat_id: Int = 0
    var cat_name: String? = null
    var duty_id: Int = 0
    var duty_name: String? = null
    var end_day: String? = null
    var event_id: Int = 0
    var status: String? = null
    var title: String? = null

    var type: Int = 0
    var type_name: String? = null


    override fun getItemType(): Int {
        return gridType
    }

}


@Keep
data class GruadeEntry(
        val broadcast: List<String>,
        val message: String,
        val part_event: List<GridManagerData>,
        val todo_event: List<GridManagerData>,
        val up_event: List<GridManagerData>
)

@Keep
data class PartEvent(
        val add_date: String,
        val cat_id: Int,
        val cat_name: String,
        val duty_id: Int,
        val duty_name: String,
        val end_day: String,
        val event_id: Int,
        val status: String,
        val title: String
)

@Keep
data class TodoEvent(
        val add_date: String,
        val cat_id: Int,
        val cat_name: String,
        val end_day: String,
        val event_id: Int,
        val status: String,
        val title: String,
        val type: Int,
        val type_name: String
)

@Keep
data class UpEvent(
        val add_date: String,
        val cat_id: Int,
        val cat_name: String,
        val duty_id: Int,
        val duty_name: String,
        val end_day: String,
        val event_id: Int,
        val status: String,
        val title: String
)

