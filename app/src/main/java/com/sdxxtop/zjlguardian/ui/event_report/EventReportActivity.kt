package com.sdxxtop.zjlguardian.ui.event_report

import android.content.Intent
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityEventReportBinding
import com.sdxxtop.zjlguardian.ui.event_report.viewmodel.EventReportViewModel

class EventReportActivity : BaseActivity<ActivityEventReportBinding, EventReportViewModel>() {
    override fun vmClazz() = EventReportViewModel::class.java

    override fun layoutId() = R.layout.activity_event_report

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
    }

    override fun initView() {
        mBinding.stvTitle.tvRight.setOnClickListener {

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phsv.onActivityResult(requestCode, resultCode, data)
    }
}
