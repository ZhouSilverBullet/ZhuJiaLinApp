package com.sdxxtop.zjlguardian.ui.gridmanagement.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.GridManagerData
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_CELL
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_EMPTY_LINE
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.ITEM_MORE

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-17 15:01
 * Version: 1.0
 * Description:
 */
class GridManageAdapter : BaseMultiItemQuickAdapter<GridManagerData, BaseViewHolder>(ArrayList<GridManagerData>()) {
    init {
        addItemType(ITEM_MORE, R.layout.grid_manage_more_recycler_item)
        addItemType(ITEM_CELL, R.layout.grid_manage_cell_recycler_item)
        addItemType(ITEM_EMPTY_LINE, R.layout.grid_manager_empty_line_recycler_item)
    }

    override fun convert(helper: BaseViewHolder?, item: GridManagerData?) {
        when (item?.itemType) {
            ITEM_MORE -> {

            }
            ITEM_CELL-> {

            }
            else -> {
            }
        }
    }

}