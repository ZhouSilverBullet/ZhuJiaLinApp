package com.sdxxtop.zjlguardian.ui.department.data

import androidx.annotation.Keep

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-10-08 22:46
 * Version: 1.0
 * Description:
 */

@Keep
data class DepartmentData(
    val list: List<DepartmentDataItem>
)

@Keep
data class DepartmentDataItem(
    val add_date: String,
    val cat_id: Int,
    val cat_name: String,
    val duty_id: Int,
    val duty_name: String,
    val end_day: String,
    val event_id: Int,
    val status: String,
    val title: String
)