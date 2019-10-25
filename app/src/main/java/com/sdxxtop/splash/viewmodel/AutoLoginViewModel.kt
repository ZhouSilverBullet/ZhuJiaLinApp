package com.sdxxtop.splash.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.db.UserSession
import com.sdxxtop.zjlguardian.model.helper.HttpParams

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-20 09:36
 * Version: 1.0
 * Description:
 */
class AutoLoginViewModel : BaseViewModel() {
    val mAutoLoginSucc = MutableLiveData<Boolean>()

    fun postAutoLogin() {
        val autoToken = UserSession.getInstance().autoToken
        val expireTime = UserSession.getInstance().expireTime
        // 过期时间比当前时间比较  autoToken不为空
        if (expireTime < System.currentTimeMillis() && !TextUtils.isEmpty(autoToken)) {
            autoLogin(autoToken)
        } else {
            //不进行自动登录
            mAutoLoginSucc.value = false
        }
    }

    private fun autoLogin(autoToken: String) {
        loadOnUI({
            val params = HttpParams()
            params.put("at", autoToken)
            //这里实际上返回了结果
            RetrofitClient.apiService.postLoginAuto(params.data)
        }, {
            val userSession = UserSession.getInstance()
            userSession.saveInfo(it.auto_info.auto_token,
                    it.auto_info.expire_time,
                    it.user_info.part_id,
                    it.user_info.id,
                    it.user_info.mobile, 0)

            userSession.saveMineInfo(it.user_info.username,it.user_info.part_name)

            mAutoLoginSucc.value = true
        }, { code, msg, t ->
            UIUtils.showToast(msg)

            //自动登录失效
            mAutoLoginSucc.value = false
        })
    }
}