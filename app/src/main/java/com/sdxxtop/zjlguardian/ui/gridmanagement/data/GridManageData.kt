package com.sdxxtop.zjlguardian.ui.gridmanagement.data

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

class GridManagerData(var type: Int=ITEM_MORE,
                      //1.事件部门 2.我的代办 3.我的上报
                      var itemMoreType:Int = 1) : MultiItemEntity {


    override fun getItemType(): Int {
        return type
    }

}