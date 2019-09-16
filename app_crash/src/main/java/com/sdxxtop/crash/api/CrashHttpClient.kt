package com.sdxxtop.crash.api

import com.sdxxtop.crash.CrashSession
import com.sdxxtop.network.api.BaseRetrofitClient
import okhttp3.OkHttpClient

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 13:16
 * Version: 1.0
 * Description:
 */

object CrashHttpClient : BaseRetrofitClient() {
     val crashApiService by lazy {
         getService(CrashApiService::class.java, CrashApiService.BASE_URL)
     }

    override fun isDebug() = CrashSession.DEBUG

    override fun handleBuilder(builder: OkHttpClient.Builder) {

    }

}