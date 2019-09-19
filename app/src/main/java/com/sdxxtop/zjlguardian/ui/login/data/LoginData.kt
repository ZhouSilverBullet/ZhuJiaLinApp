package com.sdxxtop.zjlguardian.ui.login.data

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-19 14:17
 * Version: 1.0
 * Description:
 */

data class NormalLogin(
    val auto_info: AutoInfo,
    val modpw: Boolean,
    val user_info: UserInfo
)

data class AutoInfo(
    val auto_token: String,
    val expire_time: Int,
    val is_app_debug: Int
)

data class UserInfo(
    val id: Int,
    val is_action: Int,
    val mobile: String,
    val part_id: Int,
    val part_name: String,
    val status: Int,
    val username: String
)