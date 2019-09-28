package com.sdxxtop.zjlguardian.ui.commission

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.mapcore.util.it
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityCommissionBinding
import com.sdxxtop.zjlguardian.ui.commission.adapter.CommissionListAdapter
import com.sdxxtop.zjlguardian.ui.commission.viewmodel.CommissionViewModel
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity
import com.sdxxtop.zjlguardian.ui.event_report.adapter.EventReportListAdapter

class CommissionActivity : BaseActivity<ActivityCommissionBinding, CommissionViewModel>() {

    val mAdapter by lazy {
        CommissionListAdapter()
    }

    override fun vmClazz() = CommissionViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_commission

    override fun initObserve() {
        mViewModel.mReportList.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }

    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerWidth(UIUtils.dip2px(10)))
        mBinding.rv.adapter = mAdapter
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mAdapter.data[position]
            val intent = Intent(view.context, EventReportDetailActivity::class.java)
            intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_COMMISSION)
            intent.putExtra("eventId", eventReportItem.event_id)
            startActivity(intent)
        }

    }

    override fun loadData() {
        mViewModel.loadData()
    }
}
