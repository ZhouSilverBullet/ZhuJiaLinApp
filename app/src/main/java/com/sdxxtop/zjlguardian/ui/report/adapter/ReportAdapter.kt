package com.sdxxtop.zjlguardian.ui.report.adapter

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.report.ReportMessageActivity
import com.sdxxtop.zjlguardian.ui.report.data.ReportItemData
import com.sdxxtop.zjlguardian.ui.web.WebActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 10:03
 * Version: 1.0
 * Description:
 */
class ReportAdapter(val layoutId: Int = R.layout.report_recycler_item) : BaseQuickAdapter<ReportItemData, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: ReportItemData) {
        val btnAdd = helper.getView<Button>(R.id.btn_add)
        val tvNumber = helper.getView<TextView>(R.id.tv_number)
        val tvTitle = helper.getView<TextView>(R.id.tv_title)
        tvTitle.setText(item.name)
        tvNumber.setText("数据量：" + item.count)
        btnAdd.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ReportAddActivity::class.java))
            WebActivity.skipWebActivity(mContext, item.url)
        }

        helper.convertView.setOnClickListener {
            val intent = Intent(mContext, ReportMessageActivity::class.java)
            intent.putExtra("formId", item.form_id)
            mContext.startActivity(intent)
        }
    }
}