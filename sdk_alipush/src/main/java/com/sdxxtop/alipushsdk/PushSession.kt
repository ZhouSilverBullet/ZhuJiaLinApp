package com.sdxxtop.alipushsdk

import android.content.Context
import android.util.Log
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.alibaba.sdk.android.push.register.HuaWeiRegister
import com.alibaba.sdk.android.push.register.MiPushRegister

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 17:52
 * Version: 1.0
 * Description:
 */

object PushSession {

    /**
     * 初始化推送
     */
    fun initPush(applicationContext: Context, XIAOMI_ID: String, XIAOMI_KEY: String) {
        initCloudChannel(applicationContext, XIAOMI_ID, XIAOMI_KEY)
    }


    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private fun initCloudChannel(applicationContext: Context, XIAOMI_ID: String, XIAOMI_KEY: String) {
        PushServiceFactory.init(applicationContext)
        val pushService = PushServiceFactory.getCloudPushService()
        pushService.register(applicationContext, object : CommonCallback {
            override fun onSuccess(response: String) {
//                LogUtils.e(TAG, "init cloudchannel success")
                MiPushRegister.register(applicationContext, XIAOMI_ID, XIAOMI_KEY)
                //              // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
                HuaWeiRegister.register(applicationContext)
                //              //GCM/FCM辅助通道注册
                //               GcmRegister.register(this, sendId, applicationId); //sendId/applicationId为步骤获得的参数
                val deviceId = pushService.deviceId
                Log.e("deviceId", "==$deviceId")
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
//                LogUtils.e(TAG, "init cloudchannel failed -- errorcode:$errorCode -- errorMessage:$errorMessage")
            }
        })
//        LogUtils.e(TAG, "init cloudchannel " + Thread.currentThread())

    }
}