package com.sdxxtop.zjlguardian.model.api

import com.sdxxtop.network.helper.data.BaseResponse
import com.sdxxtop.zjlguardian.model.data.InitData
import com.sdxxtop.zjlguardian.ui.event_report.data.CatDataList
import com.sdxxtop.zjlguardian.ui.login.data.NormalLogin
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-29 20:21
 * Version: 1.0
 * Description:
 */

interface ApiService {
    companion object {
        const val BASE_URL = "http://yinanapi.sdxxtop.com/api/"
    }

    @FormUrlEncoded
    @POST("app/init")
    suspend fun postAppInit(@Field("data") data: String): BaseResponse<InitData>

    @FormUrlEncoded
    @POST("login/auto")
    suspend fun postLoginAuto(@Field("data") data: String): BaseResponse<NormalLogin>

    @FormUrlEncoded
    @POST("login/normal")
    suspend fun postLoginNormal(@Field("data") data: String): BaseResponse<NormalLogin>

    @FormUrlEncoded
    @POST("login/sendCode")
    suspend fun postLoginSendCode(@Field("data") data: String): BaseResponse<Any>

    @FormUrlEncoded
    @POST("login/modpw")
    suspend fun postLoginModpw(@Field("data") data: String): BaseResponse<Any>


    @Multipart
    @POST("event/add")
    suspend fun postEventAdd(@PartMap map: HashMap<String, RequestBody>): BaseResponse<Any>

    @FormUrlEncoded
    @POST("event/cat")
    suspend fun postEventCat(@Field("data") data: String): BaseResponse<CatDataList>
}