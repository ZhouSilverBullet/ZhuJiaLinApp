package com.sdxxtop.zjlguardian.ui.message.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-25 16:05
 * Version: 1.0
 * Description:
 */

data class MessageListData(
    val list: List<MessageItemData>
)

data class MessageItemData(
    val add_time: String,
    val main_id: Int,
    val message_id: Int,
    val title: String,
    val type: Int
)

data class MessageDetails(
    val details: MDetails
)

data class MDetails(
    val add_time: String,
    val content: String,
    val title: String
)