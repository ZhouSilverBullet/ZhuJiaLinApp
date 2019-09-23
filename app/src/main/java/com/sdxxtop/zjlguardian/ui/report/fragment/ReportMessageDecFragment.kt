package com.sdxxtop.zjlguardian.ui.report.fragment

import com.sdxxtop.base.BaseFragment
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentReportMessageDecBinding
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportMessageDecViewModel

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:31
 * Version: 1.0
 * Description:
 */
class ReportMessageDecFragment:BaseFragment<FragmentReportMessageDecBinding, ReportMessageDecViewModel>() {
    override fun vmClazz() =ReportMessageDecViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
    }

    override fun layoutId() = R.layout.fragment_report_message_dec

    override fun initView() {
    }

    override fun loadData() {
        mLoadService.showSuccess()
    }
}