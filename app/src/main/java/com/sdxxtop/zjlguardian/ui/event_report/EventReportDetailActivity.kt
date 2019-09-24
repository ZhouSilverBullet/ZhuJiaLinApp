package com.sdxxtop.zjlguardian.ui.event_report

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.mapcore.util.la
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportDetailBinding
import com.sdxxtop.zjlguardian.ui.event_report.adapter.ImageHorizontalAdapter
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportDetailViewModel

class EventReportDetailActivity : BaseActivity<ActivityEventReportDetailBinding, EventReportDetailViewModel>() {
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
        mViewModel.loadData(eventId)

    }
}
