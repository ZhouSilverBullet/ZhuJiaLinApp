package com.sdxxtop.zjlguardian.ui.event_report.data

import androidx.annotation.Keep

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-20 15:29
 * Version: 1.0
 * Description:
 */

@Keep
data class CatDataList(
        val list: List<CatData>
)

@Keep
data class CatData(
        val cat_id: Int,
        val name: String
)

@Keep
data class ReportListData(
        val list: List<EventReportItem>
)

@Keep
data class EventReportItem(
        val add_date: String,
        val cat_id: Int,
        val cat_name: String,
        val duty_id: Int,
        val duty_name: String,
        val end_day: String,
        val event_id: Int,
        val status: String,
        val title: String,
        //我的上报字段
        val type:Int,
        val type_name:String
)

data class EventReportDetailData(
        val list: DetailData
)

data class DetailData(
        val cat_id: Int,
        val cat_name: String ,
        val content: String,
        val duty_id: Int,
        val duty_name: String,
        val event_id: Int,
        val finish_time: String,
        val img: List<String>,
        val place: String,
        val reply: List<Reply>,
        val settle_date: String,
        val settle_time: String,
        val title: String,
        val status: Int,
        val add_time: String,
        val user_name: String,

        val name: String,
        val mobile: String,
        val desc: String,

        val part: String,
        val job: String,
        val prove_img: List<String>
)

data class Reply(
        val desc: String,
        val main_id: Int,
        val quote_id: Int,
        val reply_id: Int
)