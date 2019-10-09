package com.sdxxtop.zjlguardian.ui.report.fragment


import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.base.ext.topViewPadding
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentReportBinding
import com.sdxxtop.zjlguardian.ui.report.adapter.ReportAdapter
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportViewModel

/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : BaseFragment<FragmentReportBinding, ReportViewModel>() {

    val mAdapter by lazy {
        ReportAdapter()
    }

    override fun vmClazz() = ReportViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.fragment_report

    override fun initObserve() {
        mViewModel.mReportList.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }


    override fun initView() {
        topViewPadding(mBinding.stvTitle)

        mBinding.rv.layoutManager = LinearLayoutManager(activity)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerWidth(UIUtils.dip2px(10)).setDividerColor(0xEFEFF4))
        mBinding.rv.adapter = mAdapter

    }

    override fun initData() {
        mLoadService.showSuccess()

//        val strArrayList = ArrayList<String>()
//        strArrayList.add("1")
//        strArrayList.add("2")
//        mAdapter.addData(strArrayList)
    }

    override fun loadData() {
        mViewModel.loadData()
    }

}
