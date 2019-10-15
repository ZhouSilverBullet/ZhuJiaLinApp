package com.sdxxtop.zjlguardian.ui.report.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-10-09 00:13
 * Version: 1.0
 * Description:
 */
data class ReportReportListData(
        val list: List<ReportItemData>
)

data class ReportItemData(
        val form_id: Int,
        val name: String,
        val count: Int,
        val url: String
)

//details
data class ReportDetailListData(
        val list: ReportDetailData
)

data class ReportDetailData(
        val count: Int,
        val desc: String,
        val end_time: String,
        val form_id: Int,
        val name: String,
        val total_count: Int,
        val user_id: Int,
        val username: String
)

//fromData
//data class ReportFromListData(
//    val list: List<ReportFromData>
//)
//
//data class ReportFromData(
//    val bobby: String,
//    val desc: String,
//    val id: String,
//    val sex: String,
//    val username: String,
//    var childData: ChildData? = null
//)
data class ReportFromData(
        var dataId: Int? = 0,
        var value0: Any? = null,
        var value1: Any? = null,
        var value2: Any? = null,
        var value3: Any? = null,
        var value4: Any? = null,
        var value5: Any? = null,
        var value6: Any? = null,
        var value7: Any? = null,
        var value8: Any? = null,
        var value9: Any? = null,
        var value10: Any? = null,
        var value11: Any? = null,
        var value12: Any? = null,
        var value13: Any? = null,
        var value14: Any? = null,
        var value15: Any? = null,
        var value16: Any? = null,
        var value17: Any? = null,
        var value18: Any? = null,
        var value19: Any? = null,
        var value20: Any? = null,


        var childData: ChildData? = null
)
