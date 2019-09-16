package com.sdxxtop.zjlguardian.ui.login.viewmodel

import android.view.View
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.zjlguardian.ui.home.MainTabActivity
import com.sdxxtop.zjlguardian.ui.login.ForgetActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 09:29
 * Version: 1.0
 * Description:
 */
class LoginViewModel : BaseViewModel() {
    fun onForgetPassword(v: View) {
        startActivity(v.context, ForgetActivity::class.java)
    }

    fun onLoginClick(v: View) {
        startActivity(v.context, MainTabActivity::class.java)
    }
}