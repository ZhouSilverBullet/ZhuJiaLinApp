package com.sdxxtop.zjlguardian.ui.contact.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseFragment
import com.sdxxtop.base.ext.searchGone
import com.sdxxtop.base.ext.searchShow
import com.sdxxtop.base.ext.topViewPadding
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.databinding.FragmentContactBinding
import com.sdxxtop.zjlguardian.ui.contact.viewmodel.ContactViewModel

/**
 * A simple [Fragment] subclass.
 */
class ContactFragment : BaseFragment<FragmentContactBinding, ContactViewModel>() {
    var mSvViewHeight = 0
    var mSvViewWidth = 0

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun vmClazz() = ContactViewModel::class.java

    override fun layoutId() = com.sdxxtop.zjlguardian.R.layout.fragment_contact

    override fun initObserve() {
        mViewModel.mSearchShowData.observe(this, Observer {
            if (it) {
                mBinding.vBg.visibility = View.VISIBLE
                searchShow(mBinding.rlSearch, mBinding.etSearch)
//                UIUtils.showSoftInputFromWindow(mBinding.etSearch)
            }
        })
    }

    override fun initView() {
        topViewPadding(mBinding.stvTitle)
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

    override fun initData() {
    }

    override fun loadData() {
        mLoadService.showSuccess()
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
