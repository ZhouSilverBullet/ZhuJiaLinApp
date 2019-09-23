package com.sdxxtop.zjlguardian.ui.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityReportAddBinding
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportAddViewModel

class ReportAddActivity : BaseActivity<ActivityReportAddBinding, ReportAddViewModel>() {
    override fun vmClazz() = ReportAddViewModel::class.java

    override fun bindVM() {
    }


    override fun layoutId() = R.layout.activity_report_add

    override fun initObserve() {
    }


    override fun initView() {
    }

}
