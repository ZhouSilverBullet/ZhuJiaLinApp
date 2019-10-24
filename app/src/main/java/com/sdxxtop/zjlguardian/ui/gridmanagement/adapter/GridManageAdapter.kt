package com.sdxxtop.zjlguardian.ui.gridmanagement.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.commission.CommissionActivity
import com.sdxxtop.zjlguardian.ui.department.DepartmentListActivity
import com.sdxxtop.zjlguardian.ui.event_report.EventReportActivity
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity
import com.sdxxtop.zjlguardian.ui.event_report.EventReportListActivity
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.*
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

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
        addItemType(ITEM_EMPTY_VIEW, R.layout.grid_manager_empty_view_recycler_item)
    }

    override fun convert(helper: BaseViewHolder?, item: GridManagerData?) {
        when (item?.itemType) {
            ITEM_MORE -> {
                handleMore(helper, item)
            }
            ITEM_CELL -> {
                handleCell(helper, item)
            }
            ITEM_EMPTY_VIEW -> {

            }
            else -> {
            }
        }
    }

    private fun handleMore(helper: BaseViewHolder?, item: GridManagerData) {
        val tvTitle = helper?.getView<TextView>(R.id.tv_title)
        val llMore = helper?.getView<LinearLayout>(R.id.ll_more)
        val ivIcon = helper?.getView<ImageView>(R.id.iv_left_icon)

        when (item.eventItemType) {
            EVENT_DEPARTMENT -> {
                tvTitle?.text = "部门事件"
                ivIcon?.setImageResource(R.drawable.grid_manager_depart)
                llMore?.setOnClickListener {
                    val intent = Intent(mContext, DepartmentListActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
            EVENT_MINE -> {
                tvTitle?.text = "我的待办"
                ivIcon?.setImageResource(R.drawable.grid_manager_mine_todo)
                llMore?.setOnClickListener {
                    val intent = Intent(mContext, CommissionActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
            EVENT_REPORT -> {
                tvTitle?.text = "我的上报"
                ivIcon?.setImageResource(R.drawable.grid_manager_report)

                llMore?.setOnClickListener {
                    val intent = Intent(mContext, EventReportListActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
            else -> {
            }
        }
    }

    private fun handleCell(helper: BaseViewHolder?, item: GridManagerData) {
        val ivIcon = helper?.getView<ImageView>(R.id.iv_cell_left_icon)
        val tvTypeTitle = helper?.getView<TextView>(R.id.tv_type_title)
        val tvDate = helper?.getView<TextView>(R.id.tv_date)
        val tvName = helper?.getView<TextView>(R.id.tv_name)

        tvDate?.text = StringBuilder("截止日期：").append(item.end_day)

        val vLine = helper?.getView<View>(R.id.v_line)
        //最后一个不显示线
        if (item.isLast) {
            vLine?.visibility = View.GONE
        } else {
            vLine?.visibility = View.VISIBLE
        }



        when (item.eventItemType) {
            EVENT_DEPARTMENT -> {

//                tvTitle?.text = "部门事件"
                tvTypeTitle?.text = StringBuilder(item.status ?: "").append("：").append(item.title)

                tvName?.text = item.duty_name

                helper?.getView<RelativeLayout>(R.id.rl_root)?.setOnClickListener {
                    val intent = Intent(mContext, EventReportDetailActivity::class.java)
//                    intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_EVENT)
//                    intent.putExtra(EventReportDetailActivity.REQUEST_TYPE, item.type)
//                    intent.putExtra("eventId", item.event_id)
//                    mContext.startActivity(intent)

                    if(item.type == 3) {
                        intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_EVENT)
                        intent.putExtra("eventId", item.event_id)
                        mContext.startActivity(intent)
                    } else {
                        intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_COMMISSION)
                        intent.putExtra(EventReportDetailActivity.REQUEST_TYPE, item.type)
                        intent.putExtra("eventId", item.event_id)
                        mContext.startActivity(intent)
                    }
                }
            }
            EVENT_MINE -> {
//                tvTitle?.text = "我的待办"
                tvTypeTitle?.text = StringBuilder(item.type_name
                        ?: "").append("：").append(item.title)

                tvName?.text = ""

                helper?.getView<RelativeLayout>(R.id.rl_root)?.setOnClickListener {
                    val intent = Intent(mContext, EventReportDetailActivity::class.java)
                    intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_COMMISSION)
                    intent.putExtra(EventReportDetailActivity.REQUEST_TYPE, item.type)
                    intent.putExtra(EventReportDetailActivity.IS_COMMISSION, true)
                    intent.putExtra("eventId", item.event_id)
                    mContext.startActivity(intent)
                }
            }
            EVENT_REPORT -> {
//                tvTitle?.text = "我的上报"
                tvTypeTitle?.text = StringBuilder(item.status ?: "").append("：").append(item.title)

                tvName?.text = ""

                helper?.getView<RelativeLayout>(R.id.rl_root)?.setOnClickListener {
                    val intent = Intent(mContext, EventReportDetailActivity::class.java)
                    intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_EVENT)
                    intent.putExtra(EventReportDetailActivity.REQUEST_TYPE, item.type)
                    intent.putExtra("eventId", item.event_id)
                    mContext.startActivity(intent)
                }
            }
            else -> {
            }
        }
    }

}