package com.sdxxtop.zjlguardian.ui.event_report

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportListBinding
import com.sdxxtop.zjlguardian.ui.event_report.adapter.EventReportListAdapter
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportListViewModel

class EventReportListActivity : BaseActivity<ActivityEventReportListBinding, EventReportListViewModel>() {
    val mAdapter by lazy {
        EventReportListAdapter()
    }

    override fun vmClazz() = EventReportListViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_event_report_list

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
            intent.putExtra("eventId", eventReportItem.event_id)
            startActivity(intent)
        }
    }

    override fun initData() {
        mViewModel.loadData()
    }

}
