package com.sdxxtop.zjlguardian.ui.report.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.ui.loadsir.EmptyCallback
import com.sdxxtop.ui.loadsir.ErrorCallback
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentReportMessageDecBinding
import com.sdxxtop.zjlguardian.ui.report.ReportMessageActivity
import com.sdxxtop.zjlguardian.ui.report.viewmodel.ReportMessageDecViewModel

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:31
 * Version: 1.0
 * Description:
 */
class ReportMessageDecFragment : BaseFragment<FragmentReportMessageDecBinding, ReportMessageDecViewModel>() {
    companion object {
        fun newInstance(formId: Int): ReportMessageDecFragment {
            val f = ReportMessageDecFragment()
            val bundle = Bundle()
            bundle.putInt("formId", formId)
            f.arguments = bundle
            return f
        }
    }

    override fun vmClazz() = ReportMessageDecViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.click = this
    }

    override fun initObserve() {
        mViewModel.mReportDetail.observe(this, Observer {
            if (it != null) {
                (_mActivity as ReportMessageActivity).setTitle(it.name)
            }
            mLoadService.showSuccess()
        })

        mViewModel.mThrowable.observe(this, Observer {
            if (it.code == 201) {
                mLoadService.showCallback(EmptyCallback::class.java)
            } else {
                mLoadService.showCallback(ErrorCallback::class.java)
            }
        })
    }

    override fun layoutId() = R.layout.fragment_report_message_dec

    override fun initView() {
        val formId = arguments?.getInt("formId")
        mViewModel.formId = formId ?: 0
    }

    override fun loadData() {
        mViewModel.loadData()
    }

    override fun onClick(v: View) {
        when(v) {
            mBinding.tvSkipData -> {
                activityChangeDataFragment()
            }
            mBinding.llSkipData -> {
                activityChangeDataFragment()
            }
        }
    }

    fun activityChangeDataFragment() {
        (activity as ReportMessageActivity).activityChangeDataFragment()
    }
}