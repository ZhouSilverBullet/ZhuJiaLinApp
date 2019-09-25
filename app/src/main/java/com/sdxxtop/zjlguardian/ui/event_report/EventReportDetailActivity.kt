package com.sdxxtop.zjlguardian.ui.event_report

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportDetailBinding
import com.sdxxtop.zjlguardian.ui.event_report.adapter.ImageHorizontalAdapter
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportDetailViewModel

class EventReportDetailActivity : BaseActivity<ActivityEventReportDetailBinding, EventReportDetailViewModel>() {
    companion object {
        val KEY_EVENT_TYPE = "type"
        val TYPE_EVENT = 0 //事件处理详情
        val TYPE_SELF = 1 //自行处理
        val TYPE_COMMISSION = 2 //代办
    }

    val keyEventType by lazy {
        intent.getIntExtra(KEY_EVENT_TYPE, 0)
    }

    val mAdapter by lazy {
        ImageHorizontalAdapter()
    }

    override fun vmClazz() = EventReportDetailViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_event_report_detail

    override fun initObserve() {
        mViewModel.mDetailData.observe(this, Observer {
            mBinding.tcvTitle.tvContentText = it.title
            mBinding.tcvClassify.tvContentText = it.cat_name
            mBinding.tcvPlace.tvContentText = it.place
            mBinding.tcvDate.tvContentText = it.settle_time
            mBinding.tvContentText.text = it.content

        })
    }

    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.rv.adapter = mAdapter


    }

    override fun initData() {
        val eventId = intent.getIntExtra("eventId", 0)

        val title = when (keyEventType) {
            TYPE_EVENT -> "事件详情"
            TYPE_SELF -> {
                mBinding.tcvDate.visibility = View.GONE
                "自行处理详情"
            }
            else -> "自行处理详情"
        }

        mBinding.stvTitle.setTitleValue(title)
        mViewModel.loadData(eventId, keyEventType)

    }
}
