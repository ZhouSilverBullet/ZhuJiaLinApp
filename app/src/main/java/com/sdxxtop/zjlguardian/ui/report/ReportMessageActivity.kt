package com.sdxxtop.zjlguardian.ui.report

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
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
        val formId = intent.getIntExtra("formId", 0)

        val titleList = ArrayList<String>()
        titleList.add("概述")
        titleList.add("数据")

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(ReportMessageDecFragment.newInstance(formId))
        fragmentList.add(ReportMessageDataFragment.newInstance(formId))

        mBinding.vp.adapter = ReportPagerAdapter(supportFragmentManager, titleList, fragmentList)
        mBinding.vp.setPagingEnabled(false)
        mBinding.tab.setupWithViewPager(mBinding.vp)
        mBinding.tab.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.text == "概述") {
                    (fragmentList[1] as ReportMessageDataFragment).leavePage()
                }
            }

        })
    }

    fun setTitle(title: String) {
        mBinding.stvTitle.setTitleValue(title)
    }

    fun activityChangeDataFragment() {
        mBinding.vp.currentItem = 1
    }

}

