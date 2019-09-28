package com.sdxxtop.zjlguardian.ui.event_report

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportListBinding
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.ui.contact.data.TYPE_PART_DATA
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity.Companion.TYPE_EVENT
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity.Companion.TYPE_SELF
import com.sdxxtop.zjlguardian.ui.event_report.adapter.EventReportListAdapter
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportListViewModel
import com.sdxxtop.zjlguardian.ui.self_handle.adapter.SelfHandleListAdapter

class EventReportListActivity : BaseActivity<ActivityEventReportListBinding, EventReportListViewModel>() {
    val mAdapter by lazy {
        EventReportListAdapter()
    }

    val mSelfAdapter by lazy {
        SelfHandleListAdapter()
    }

    val keyEventType  by lazy {
        intent.getIntExtra(EventReportDetailActivity.KEY_EVENT_TYPE, 0)
    }

    override fun vmClazz() = EventReportListViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_event_report_list

    override fun initObserve() {
        mViewModel.mReportList.observe(this, Observer {
            if (keyEventType == 0) {
                mAdapter.replaceData(it)
            } else {
                mSelfAdapter.replaceData(it)
            }
        })
    }


    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerColor(0xE6E6E6).setDividerWidth(UIUtils.dip2px(10)))
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mAdapter.data[position]
            val intent = Intent(view.context, EventReportDetailActivity::class.java)
            intent.putExtra("eventId", eventReportItem.event_id)
            startActivity(intent)
        }

        mSelfAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mSelfAdapter.data[position]
            val intent = Intent(view.context, EventReportDetailActivity::class.java)
            intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_SELF)
            intent.putExtra("eventId", eventReportItem.event_id)
            startActivity(intent)
        }

    }

    override fun initData() {

        if (keyEventType == TYPE_EVENT) {
            mBinding.stvTitle.setTitleValue("我的上报")
            mBinding.rv.adapter = mAdapter
        } else if(keyEventType == TYPE_SELF){
            mBinding.stvTitle.setTitleValue("我的处理")
            mBinding.rv.adapter = mSelfAdapter
        }
        mViewModel.loadData(keyEventType)
    }

}
