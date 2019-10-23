package com.sdxxtop.zjlguardian.ui.event_report.data

import androidx.annotation.Keep

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-28 17:09
 * Version: 1.0
 * Description:
 */
@Keep
data class PartListData(
    val list: List<PartListItem>
)

@Keep
data class PartListItem(
    val part_id: Int,
    val part_name: String,
    val users: List<User>
)

@Keep
data class User(
    val id: Int,
    val username: String
)