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
    val name: String
)