package com.sdxxtop.zjlguardian.ui.report.adapter

import android.content.Intent
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.report.ReportAddActivity
import com.sdxxtop.zjlguardian.ui.report.ReportMessageActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 10:03
 * Version: 1.0
 * Description:
 */
class ReportAdapter(val layoutId: Int = R.layout.report_recycler_item) : BaseQuickAdapter<String, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: String) {
        val btnAdd = helper.getView<Button>(R.id.btn_add)
        btnAdd.setOnClickListener {
            mContext.startActivity(Intent(mContext, ReportAddActivity::class.java))
        }

        helper.convertView.setOnClickListener {
            mContext.startActivity(Intent(mContext, ReportMessageActivity::class.java))
        }
    }
}