package com.sdxxtop.crash.db

import com.sdxxtop.crash.data.CrashData

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 15:32
 * Version: 1.0
 * Description:
 */
interface CrashDBHelper {

    /**
     * 查询 crashData
     */
    fun queryCrashDataIsNotPush(): List<CrashData>

    /**
     * 插入 crashData
     */
    fun installCrashData(crashData: CrashData)

    /**
     * 删除 crashData 已经上传过的
     */
    fun deleteCrashDataIsPush()

}