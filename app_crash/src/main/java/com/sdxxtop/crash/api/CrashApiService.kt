package com.sdxxtop.crash.api

import com.sdxxtop.network.helper.data.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 12:00
 * Version: 1.0
 * Description:
 */
interface CrashApiService {
    companion object {
        val BASE_URL = "http://wap.sdxxtop.com/app/"
    }

    @FormUrlEncoded
    @POST("crash/addCrash")
    suspend fun addCrash(@Field("data[]") data: List<String>): BaseResponse<Any>

}