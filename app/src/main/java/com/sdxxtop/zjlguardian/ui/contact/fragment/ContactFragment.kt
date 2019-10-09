package com.sdxxtop.zjlguardian.ui.contact.fragment

import android.text.TextUtils
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.ext.searchGone
import com.sdxxtop.base.ext.searchShow
import com.sdxxtop.base.ext.topViewPadding
import com.sdxxtop.base.title.BaseTitleFragment
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentContactBinding
import com.sdxxtop.zjlguardian.ui.contact.adapter.ContactAdapter
import com.sdxxtop.zjlguardian.ui.contact.viewmodel.ContactViewModel

/**
 * A simple [Fragment] subclass.
 */
class ContactFragment : BaseTitleFragment<FragmentContactBinding, ContactViewModel>() {
    var mSvViewHeight = 0
    var mSvViewWidth = 0

    val mAdapter by lazy {
        ContactAdapter()
    }

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun vmClazz() = ContactViewModel::class.java

    override fun layoutId() = R.layout.fragment_contact

    override fun initObserve() {
        mViewModel.mSearchShowData.observe(this, Observer {
            if (it) {
                mBinding.vBg.visibility = View.VISIBLE
                searchShow(mBinding.rlSearch, mBinding.etSearch)
//                UIUtils.showSoftInputFromWindow(mBinding.etSearch)
            }
        })

        mViewModel.mUserData.observe(this, Observer {
            mAdapter.replaceData(it)
        })


    }

    override fun initView() {
        topViewPadding(getTitleView())

        setTitleValue("通讯录")
        setTitleColor(R.color.white)
        setBgColor(R.color.colorPrimary)

        mBinding.rv.layoutManager = LinearLayoutManager(activity)
//        mBinding.rv.addItemDecoration(ItemDivider())
        mAdapter.setEmptyView(R.layout.contact_empty_view, mBinding.rv)
        mBinding.rv.adapter = mAdapter



//        val headersDecor = StickyRecyclerHeadersDecoration(mAdapter) //绑定之前的adapter
//
//        mAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//            override fun onChanged() {
//                headersDecor.invalidateHeaders()
//            }
//        })
//        mBinding.rv.addItemDecoration(headersDecor)
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

        mBinding.etSearch.addTextChangedListener {
            if (TextUtils.isEmpty(it)) {
                mViewModel.searchData("")
            } else {
                mViewModel.searchData(it.toString())
            }
        }
    }

    override fun initData() {
    }

    override fun loadData() {
        mViewModel.searchData("")

        mLoadService.showSuccess()
    }

    override fun onBackPressedSupport(): Boolean {
        if (mBinding.rlSearch.visibility == View.VISIBLE) {
            //当search显示的时候，按返回键，就隐藏search
            searchGone(mBinding.rlSearch, mBinding.vBg)
            mViewModel.searchData("")
            mBinding.etSearch.setText("")
            return true
        }
        return super.onBackPressedSupport()
    }

}
