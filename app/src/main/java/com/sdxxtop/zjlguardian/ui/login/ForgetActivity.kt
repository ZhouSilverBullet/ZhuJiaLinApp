package com.sdxxtop.zjlguardian.ui.login

import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityForgetBinding
import com.sdxxtop.zjlguardian.ui.login.viewmodel.ForgetViewModel

class ForgetActivity : BaseActivity<ActivityForgetBinding, ForgetViewModel>() {
    override fun vmClazz() = ForgetViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_forget

    override fun initObserve() {

    }

    override fun initView() {

    }

}
