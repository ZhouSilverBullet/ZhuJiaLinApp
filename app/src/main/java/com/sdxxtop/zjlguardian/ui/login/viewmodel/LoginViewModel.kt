package com.sdxxtop.zjlguardian.ui.login.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.App
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.db.UserSession
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

    val mSkipLogin = MutableLiveData<Boolean>()

    val phone = ObservableField<String>()
    val password = ObservableField<String>()

//    fun onForgetPassword(v: View) {
//        startActivity(v.context, ForgetActivity::class.java)
//    }

    fun onLoginClick(v: View) {
        showLoadingDialog(true)
        val phoneText = phone.get()
        if (phoneText?.length != 11) {
            showLoadingDialog(false)
            UIUtils.showToast("请输入正确的手机号码")
            return
        }

        val passwordText = password.get()
        if (passwordText == null || passwordText.length < 6 || passwordText.length > 20) {
            showLoadingDialog(false)
            UIUtils.showToast(App.INSTANCE.getString(R.string.password_format))
            return
        }


        login(v, phoneText, passwordText)


//        startActivity(v.context, MainTabActivity::class.java)
    }

    private fun login(v: View, phoneText: String, passwordText: String) {
        loadOnUI({
            val params = HttpParams()
            params.put("mb", phoneText)
            params.put("pw", passwordText)
            //这里实际上返回了结果
            RetrofitClient.apiService.postLoginNormal(params.data)
        }, {

            //这个结果如果是true的话就要进行密码修改
            //否则就登录成功了
            if (!it.modpw) {
                val userSession = UserSession.getInstance()
                userSession.saveInfo(it.auto_info.auto_token,
                        it.auto_info.expire_time,
                        it.user_info.part_id,
                        it.user_info.id,
                        it.user_info.mobile, 0)

                userSession.saveMineInfo(it.user_info.username,it.user_info.part_name)
            }

            mSkipLogin.value = it.modpw

            showLoadingDialog(false)
        }, { code, msg, t ->

            UIUtils.showToast(msg)
            showLoadingDialog(false)
        })
    }
}