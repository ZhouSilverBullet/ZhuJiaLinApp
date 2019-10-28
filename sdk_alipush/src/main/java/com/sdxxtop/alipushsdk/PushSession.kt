package com.sdxxtop.alipushsdk

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.alibaba.sdk.android.push.register.HuaWeiRegister
import com.alibaba.sdk.android.push.register.MiPushRegister
import com.sdxxtop.common.utils.LogUtil

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 17:52
 * Version: 1.0
 * Description:
 */

object PushSession {
    val TAG = "PushSession"

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
        initNotificationChannel(applicationContext)
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
                Log.e("PushSession ", " deviceId ==$deviceId")
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:$errorCode -- errorMessage:$errorMessage")
            }
        })
        LogUtil.e(TAG, "init cloudchannel " + Thread.currentThread())

    }

    //Android 8.0以上设备通知接收
    private fun initNotificationChannel(applicationContext: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // 通知渠道的id
            val id = "1"
            // 用户可以看到的通知渠道的名字.
            val name = "数字沂南"
            // 用户可以看到的通知渠道的描述
            val description = "数字沂南推送通知"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            // 配置通知渠道的属性
            mChannel.description = description
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(false)
            mChannel.lightColor = Color.RED
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(false)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }
}