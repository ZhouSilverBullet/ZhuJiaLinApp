package com.sdxxtop.zjlguardian.ui.event_report.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-20 15:29
 * Version: 1.0
 * Description:
 */

data class CatDataList(
        val list: List<CatData>
)

data class CatData(
        val cat_id: Int,
        val name: String
)


data class ReportListData(
        val list: List<EventReportItem>
)

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
        val title: String
)

data class Reply(
        val desc: String,
        val main_id: Int,
        val quote_id: Int,
        val reply_id: Int
)