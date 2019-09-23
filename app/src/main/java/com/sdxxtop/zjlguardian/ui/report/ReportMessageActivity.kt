package com.sdxxtop.zjlguardian.ui.report

import androidx.fragment.app.Fragment
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityReportMessageBinding
import com.sdxxtop.zjlguardian.databinding.FragmentReportMessageDataBinding
import com.sdxxtop.zjlguardian.ui.report.adapter.ReportPagerAdapter
import com.sdxxtop.zjlguardian.ui.report.fragment.ReportMessageDataFragment
import com.sdxxtop.zjlguardian.ui.report.fragment.ReportMessageDecFragment
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportMessageDecViewModel
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportMessageViewModel

class ReportMessageActivity : BaseActivity<ActivityReportMessageBinding, ReportMessageViewModel>() {
    override fun vmClazz() = ReportMessageViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_report_message

    override fun initObserve() {
    }


    override fun initView() {
        val titleList = ArrayList<String>()
        titleList.add("概述")
        titleList.add("数据")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(ReportMessageDecFragment())
        fragmentList.add(ReportMessageDataFragment())

        mBinding.vp.adapter = ReportPagerAdapter(supportFragmentManager, titleList, fragmentList)
        mBinding.vp.setPagingEnabled(false)
        mBinding.tab.setupWithViewPager(mBinding.vp)
    }


}
