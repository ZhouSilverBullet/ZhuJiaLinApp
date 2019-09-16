package com.sdxxtop.crash.data

import android.os.Build
import com.google.gson.annotations.Expose
import com.sdxxtop.crash.CrashSession
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 13:22
 * Version: 1.0
 * Description:
 */
open class CrashData(
        var userid: String = "",
        var crash_info: String = "",
        var crash_time: String = "",
//        var isPushServiceSuccess: Boolean = false,
        var crash_reason: String = "",
        var app_id: String = CrashSession.getPackageName(),
        var app_name: String = CrashSession.getAppName(),
        //app的版本号
        var app_version: String = CrashSession.versionName,
        //XIAOMI
        var phone_model: String = Build.BRAND,
        //android 系统版本号
        var system_version: String = Build.VERSION.SDK_INT.toString(),
        var app_platform: String = "1") : RealmObject()