package com.sdxxtop.zjlguardian.ui.login.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.network.helper.DefaultParams
import com.sdxxtop.zjlguardian.App
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.home.MainTabActivity
import com.sdxxtop.zjlguardian.ui.login.ForgetActivity
import es.dmoral.toasty.Toasty

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 09:29
 * Version: 1.0
 * Description:
 */
class LoginViewModel : BaseViewModel() {
    val phone = ObservableField<String>()
    val password = ObservableField<String>()

    fun onForgetPassword(v: View) {
        startActivity(v.context, ForgetActivity::class.java)
    }

    fun onLoginClick(v: View) {

        login()


//        startActivity(v.context, MainTabActivity::class.java)
    }

    private fun login() {
        loadOnUI({
            val params = HttpParams()
            params.put("mb", phone.get())
            params.put("pw", password.get())
            //这里实际上返回了结果
            RetrofitClient.apiService.postLoginNormal(params.data)
        }, {
            //            Logger.e(it.toString())
//            mInitData.value = it
        }, { code, msg, t ->
            Toasty.error(App.INSTANCE, msg).show()
        })
    }
}