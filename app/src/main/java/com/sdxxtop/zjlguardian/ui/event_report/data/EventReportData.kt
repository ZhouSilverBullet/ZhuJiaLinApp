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