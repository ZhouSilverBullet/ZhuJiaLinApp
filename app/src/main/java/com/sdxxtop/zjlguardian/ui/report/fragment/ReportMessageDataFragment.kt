package com.sdxxtop.zjlguardian.ui.report.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.base.ext.searchGone
import com.sdxxtop.base.ext.searchShow
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentReportMessageDataBinding
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportMessageDataViewModel

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:31
 * Version: 1.0
 * Description:
 */
class ReportMessageDataFragment : BaseFragment<FragmentReportMessageDataBinding, ReportMessageDataViewModel>() {

    var mSvViewHeight = 0
    var mSvViewWidth = 0

    override fun vmClazz() = ReportMessageDataViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initObserve() {
        mViewModel.mSearchShowData.observe(this, Observer {
            if (it) {
                mBinding.vBg.visibility = View.VISIBLE
                searchShow(mBinding.rlSearch, mBinding.etSearch)
//                UIUtils.showSoftInputFromWindow(mBinding.etSearch)
            }
        })
    }

    override fun layoutId() = R.layout.fragment_report_message_data

    override fun initView() {
    }

    override fun loadData() {
        mLoadService.showSuccess()
    }

    override fun initEvent() {
        mBinding.svView.viewTreeObserver.addOnGlobalLayoutListener {
            if (mSvViewHeight == 0) {
                mSvViewHeight = mBinding.svView.height
            }
            if (mSvViewWidth == 0) {
                mSvViewWidth = mBinding.svView.width
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        if (mBinding.rlSearch.visibility == View.VISIBLE) {
            //当search显示的时候，按返回键，就隐藏search
            searchGone(mBinding.rlSearch, mBinding.vBg)

            return true
        }
        return super.onBackPressedSupport()
    }
}