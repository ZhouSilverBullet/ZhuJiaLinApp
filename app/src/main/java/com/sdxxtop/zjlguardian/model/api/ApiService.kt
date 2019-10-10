package com.sdxxtop.zjlguardian.model.api

import com.sdxxtop.network.helper.data.BaseResponse
import com.sdxxtop.zjlguardian.model.data.InitData
import com.sdxxtop.zjlguardian.model.imgpush.ImageData
import com.sdxxtop.zjlguardian.ui.contact.data.ContactData
import com.sdxxtop.zjlguardian.ui.department.data.DepartmentData
import com.sdxxtop.zjlguardian.ui.event_report.data.CatDataList
import com.sdxxtop.zjlguardian.ui.event_report.data.EventReportDetailData
import com.sdxxtop.zjlguardian.ui.event_report.data.PartListData
import com.sdxxtop.zjlguardian.ui.event_report.data.ReportListData
import com.sdxxtop.zjlguardian.ui.gridmanagement.data.GruadeEntry
import com.sdxxtop.zjlguardian.ui.login.data.NormalLogin
import com.sdxxtop.zjlguardian.ui.message.data.MessageDetails
import com.sdxxtop.zjlguardian.ui.message.data.MessageListData
import com.sdxxtop.zjlguardian.ui.report.data.ReportReportListData
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

    @FormUrlEncoded
    @POST("main/index")
    suspend fun postMainIndex(@Field("data") data: String): BaseResponse<GruadeEntry>

    @Multipart
    @POST("event/add")
    suspend fun postEventAdd(@PartMap map: HashMap<String, RequestBody>): BaseResponse<String>

    @FormUrlEncoded
    @POST("event/cat")
    suspend fun postEventCat(@Field("data") data: String): BaseResponse<CatDataList>

    @FormUrlEncoded
    @POST("event/lists")
    suspend fun postEventLists(@Field("data") data: String): BaseResponse<ReportListData>


//    @FormUrlEncoded
//    @POST("event/details")
//    suspend fun postEventDetails(@Field("data") data: String): BaseResponse<EventReportDetailData>

    /**
     * path:
     *  event/details
     *  eventself/details
     *  event/todo
     */
    @FormUrlEncoded
    @POST("{path}")
    suspend fun postEventDetails(@Path("path") path: String, @Field("data") data: String): BaseResponse<EventReportDetailData>

    @Multipart
    @POST("event/upimg")
    suspend fun postUpimg(@PartMap map: HashMap<String, RequestBody>): BaseResponse<ImageData>

    //受理
    @FormUrlEncoded
    @POST("event/settle")
    suspend fun postEventSettle(@Field("data") data: String): BaseResponse<Any>

    //解决
    @FormUrlEncoded
    @POST("event/finish")
    suspend fun postEventFinish(@Field("data") data: String): BaseResponse<Any>

    //流转
    @FormUrlEncoded
    @POST("event/trans")
    suspend fun postEventTrans(@Field("data") data: String): BaseResponse<Any>

    //单位列表
    @FormUrlEncoded
    @POST("part/lists")
    suspend fun postPartLists(@Field("data") data: String): BaseResponse<PartListData>


    @Multipart
    @POST("eventself/add")
    suspend fun postEventSelfAdd(@PartMap map: HashMap<String, RequestBody>): BaseResponse<String>

    @FormUrlEncoded
    @POST("eventself/details")
    suspend fun postEventSelfDetails(@Field("data") data: String): BaseResponse<EventReportDetailData>

    @FormUrlEncoded
    @POST("eventself/lists")
    suspend fun postEventSelfLists(@Field("data") data: String): BaseResponse<ReportListData>

    /**
     *  lists
     * search
     */
    @FormUrlEncoded
    @POST("phone/{path}")
    suspend fun postPhoneLists(@Path("path") path: String, @Field("data") data: String): BaseResponse<ContactData>

    @FormUrlEncoded
    @POST("phone/search")
    suspend fun postPhoneSearch(@Field("data") data: String): BaseResponse<ContactData>


    @FormUrlEncoded
    @POST("event/index")
    suspend fun postEventIndex(@Field("data") data: String): BaseResponse<ReportListData>

    @FormUrlEncoded
    @POST("event/done")
    suspend fun postEventDone(@Field("data") data: String): BaseResponse<ReportListData>


    @FormUrlEncoded
    @POST("message/lists")
    suspend fun postMessageLists(@Field("data") data: String): BaseResponse<MessageListData>

    @FormUrlEncoded
    @POST("message/details")
    suspend fun postMessageDetails(@Field("data") data: String): BaseResponse<MessageDetails>


    @FormUrlEncoded
    @POST("event/partlists")
    suspend fun postEventPartLists(@Field("data") data: String): BaseResponse<DepartmentData>


    @FormUrlEncoded
    @POST("collect/lists")
    suspend fun postEventCollectLists(@Field("data") data: String): BaseResponse<ReportReportListData>
}