package com.sdxxtop.zjlguardian.ui.self_handle

import android.content.Intent
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivitySelfHandleBinding
import com.sdxxtop.zjlguardian.ui.gridmanagement.fragment.GridManageFragment
import com.sdxxtop.zjlguardian.ui.self_handle.viewmodel.SelfHandleViewModel

class SelfHandleActivity : BaseActivity<ActivitySelfHandleBinding, SelfHandleViewModel>() {
    override fun vmClazz() = SelfHandleViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_self_handle

    override fun initObserve() {

    }

    override fun initView() {

        mBinding.bView.setImageLoader(GridManageFragment.GlideImageLoader())
        val arrayList = ArrayList<Int>()
        arrayList.add(R.drawable.test0)
        arrayList.add(R.drawable.test1)
        arrayList.add(R.drawable.test2)
        arrayList.add(R.drawable.test3)

        mBinding.bView.setImages(arrayList)
        mBinding.bView.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phsv.onActivityResult(requestCode, resultCode, data)
    }
}
