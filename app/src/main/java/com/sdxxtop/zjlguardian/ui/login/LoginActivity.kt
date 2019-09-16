package com.sdxxtop.zjlguardian.ui.login

import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityLoginBinding
import com.sdxxtop.zjlguardian.ui.login.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun layoutId() = R.layout.activity_login

    override fun vmClazz() = LoginViewModel::class.java

    override fun initObserve() {

    }

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun initView() {

    }
}
