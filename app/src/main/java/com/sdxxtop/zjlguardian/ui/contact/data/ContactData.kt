package com.sdxxtop.zjlguardian.ui.contact.data

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-25 09:50
 * Version: 1.0
 * Description:
 */

data class ContactData(
        val list: List<ContactPart>
)

data class ContactPart(
        val part_id: Int,
        val part_name: String,
        val users: List<UserData>
)

const val TYPE_USER_DATA = 1
const val TYPE_PART_DATA = 2

data class UserData(
        val id: Int,
        val mobile: String,
        val username: String
) : MultiItemEntity {

    override fun getItemType(): Int {
        return type
    }

    var part_name: String? = null
    var part_id: Int = 0
    var type = 0
}
