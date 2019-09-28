package com.sdxxtop.zjlguardian.ui.event_report.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-28 17:09
 * Version: 1.0
 * Description:
 */
data class PartListData(
    val list: List<PartListItem>
)

data class PartListItem(
    val part_id: Int,
    val part_name: String,
    val users: List<User>
)

data class User(
    val id: Int,
    val username: String
)