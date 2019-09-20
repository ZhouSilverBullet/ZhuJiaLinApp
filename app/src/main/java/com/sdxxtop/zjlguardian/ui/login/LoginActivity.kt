package com.sdxxtop.zjlguardian.ui.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.App
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityLoginBinding
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.db.UserSession
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.home.MainTabActivity
import com.sdxxtop.zjlguardian.ui.login.data.ForgetInfo
import com.sdxxtop.zjlguardian.ui.login.data.UserInfo
import com.sdxxtop.zjlguardian.ui.login.viewmodel.LoginViewModel
import es.dmoral.toasty.Toasty

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun layoutId() = R.layout.activity_login

    override fun vmClazz() = LoginViewModel::class.java

    override fun initObserve() {
        mViewModel.mSkipLogin.observe(this, Observer {
            if (it) {
                //跳转到修改密码
                val intent = Intent(this, ForgetActivity::class.java)
                startActivityForResult(intent, 100)
            } else {
                startActivity(Intent(this@LoginActivity, MainTabActivity::class.java))
                finish()
            }
        })
    }

    override fun bindVM() {
        mBinding.vm = mViewModel
        mBinding.click = this
    }

    override fun initView() {
        mViewModel.phone.set(UserSession.getInstance().mobile)
        mViewModel.password.set(UserSession.getInstance().password)
    }

    override fun isDarkStatusIcon(): Boolean {
        return true
    }

    override fun onClick(v: View) {
        when (v) {
            mBinding.btnForget -> {
                val intent = Intent(this, ForgetActivity::class.java)
                startActivityForResult(intent, 100)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == 100 && data != null) {
            val forgetInfo = data.getParcelableExtra<ForgetInfo>("forgetInfo")
            mViewModel.phone.set(forgetInfo.phone)
            mViewModel.password.set(forgetInfo.password)
            UIUtils.showToast("点击即可登录")
        }
    }
}
