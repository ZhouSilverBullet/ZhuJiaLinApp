package com.sdxxtop.zjlguardian.ui.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityForgetBinding
import com.sdxxtop.zjlguardian.model.db.UserSession
import com.sdxxtop.zjlguardian.ui.login.viewmodel.ForgetViewModel
import com.sdxxtop.zjlguardian.widget.CodeButtonManager
import com.sdxxtop.zjlguardian.widget.callback.SingleClickCallback

class ForgetActivity : BaseActivity<ActivityForgetBinding, ForgetViewModel>(), SingleClickCallback {

    val codeButtonManager by lazy {
        CodeButtonManager(mBinding.btnCode)
    }

    val isMineFragmentSkip by lazy {
        intent.getBooleanExtra("isMineFragmentSkip", false)
    }

    override fun vmClazz() = ForgetViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_forget

    override fun initObserve() {
        mViewModel.mForgetRequestSucc.observe(this, Observer {
            if (isMineFragmentSkip) {
                UserSession.getInstance().logout()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("isMineFragmentSkipForget", true)
                intent.putExtra("forgetInfo", it)
                startActivity(intent)
                finishAffinity()
            } else {
                val intent = Intent()
                intent.putExtra("forgetInfo", it)
                setResult(100, intent)
                finish()
            }
        })
    }

    override fun initView() {
        if (isMineFragmentSkip) {
            mViewModel.phone.set(UserSession.getInstance().userName)
        }
    }

    override fun initEvent() {
        codeButtonManager.setSingleClick(this)
    }

    override fun onSingleClick(v: View) {
        mViewModel.sendCode(codeButtonManager)
    }

    override fun onFastClick() {
    }

    override fun isDarkStatusIcon(): Boolean {
        return true
    }
}
