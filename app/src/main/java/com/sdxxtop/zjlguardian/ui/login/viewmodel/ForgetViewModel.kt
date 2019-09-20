package com.sdxxtop.zjlguardian.ui.login.viewmodel

import android.text.TextUtils
import android.view.TextureView
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.App
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.login.data.ForgetInfo
import com.sdxxtop.zjlguardian.widget.CodeButtonManager

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 15:17
 * Version: 1.0
 * Description:
 */
class ForgetViewModel : BaseViewModel() {
    val mForgetRequestSucc = MutableLiveData<ForgetInfo>()

    val phone = ObservableField<String>()
    val code = ObservableField<String>()
    val password = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    fun onForgetPush(v: View) {
        showLoadingDialog(true)

        val phoneText = phone.get()
        if (phoneText?.length != 11) {
            showLoadingDialog(false)
            UIUtils.showToast("请输入正确的手机号码")
            return
        }

        val codeStr = code.get()
        if (TextUtils.isEmpty(codeStr) || codeStr?.length != 4) {
            showLoadingDialog(false)
            UIUtils.showToast("请输入正确的验证码")
            return
        }

        val passwordStr = password.get() ?: ""
        val confirmPasswordStr = confirmPassword.get() ?: ""

        if (passwordStr.isEmpty()) {
            showLoadingDialog(false)
            UIUtils.showToast("密码不能为空")
            return
        }

        if (passwordStr.isEmpty()) {
            showLoadingDialog(false)
            UIUtils.showToast("确认密码不能为空")
            return
        }

        if (passwordStr.length < 6 || passwordStr.length > 20) {
            showLoadingDialog(false)
            UIUtils.showToast(App.INSTANCE.getString(R.string.password_format))
            return
        }

        if (passwordStr != confirmPasswordStr) {
            showLoadingDialog(false)
            UIUtils.showToast("输入的两次密码不一样")
            return
        }

        loadOnUI({
            val params = HttpParams()
            params.put("mb", phoneText)
            params.put("pw", passwordStr)
            params.put("pwc", confirmPasswordStr)
            params.put("ac", codeStr)

            //这里实际上返回了结果
            RetrofitClient.apiService.postLoginModpw(params.data)
        }, {
            //            Logger.e(it.toString())
//            mInitData.value = it
            showLoadingDialog(false)

            mForgetRequestSucc.value = ForgetInfo(phoneText, passwordStr)
        }, { code, msg, t ->
            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }

    fun sendCode(codeButtonManager: CodeButtonManager?) {
        showLoadingDialog(true)
        val phoneText = phone.get()
        if (phoneText?.length != 11) {
            showLoadingDialog(false)
            UIUtils.showToast("请输入正确的手机号码")
            return
        }

        loadBaseOnUI({
            val params = HttpParams()
            params.put("mb", phoneText)
            //这里实际上返回了结果
            RetrofitClient.apiService.postLoginSendCode(params.data)
        }, {
            //            Logger.e(it.toString())
//            mInitData.value = it
            showLoadingDialog(false)
            codeButtonManager?.startCode()
        }, { code, msg, t ->
            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}