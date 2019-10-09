package com.sdxxtop.zjlguardian.ui.report.fragment


import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.base.ext.topViewPadding
import com.sdxxtop.base.title.BaseTitleFragment
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.ui.loadsir.EmptyCallback
import com.sdxxtop.ui.loadsir.ErrorCallback
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentReportBinding
import com.sdxxtop.zjlguardian.ui.report.adapter.ReportAdapter
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportViewModel

/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : BaseTitleFragment<FragmentReportBinding, ReportViewModel>() {

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
            mLoadService.showSuccess()
            if (it.isEmpty()) {
                mLoadService.showCallback(EmptyCallback::class.java)
            } else {
                mAdapter.replaceData(it)
            }
        })

        mViewModel.mThrowable.observe(this, Observer {
            mLoadService.showCallback(ErrorCallback::class.java)
        })
    }


    override fun initView() {
        topViewPadding(getTitleView())

        setTitleValue("数据上报")
        setTitleColor(R.color.white)
        setBgColor(R.color.colorPrimary)

        mBinding.rv.layoutManager = LinearLayoutManager(activity)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerWidth(UIUtils.dip2px(10)).setDividerColor(0xEFEFF4))
        mBinding.rv.adapter = mAdapter

    }

    override fun initData() {


//        val strArrayList = ArrayList<String>()
//        strArrayList.add("1")
//        strArrayList.add("2")
//        mAdapter.addData(strArrayList)
    }

    override fun loadData() {
        mViewModel.loadData()
    }

    override fun preLoad() {
        loadData()
    }
}
