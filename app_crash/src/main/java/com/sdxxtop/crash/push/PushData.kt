package com.sdxxtop.crash.push

import com.sdxxtop.common.utils.LogUtil
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.crash.api.CrashHttpClient
import com.sdxxtop.crash.db.CrashRealmHelper
import com.sdxxtop.crash.toJson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-01 20:11
 * Version: 1.0
 * Description:
 */

object PushData {
    val mainScope = MainScope()

    @JvmStatic
    fun pushData() {
        mainScope.launch {
            val queryCrashData = CrashRealmHelper.INSTANCE.queryCrashDataIsNotPush()
            val list = ArrayList<String>()
            queryCrashData.forEach {
                val copyToRealm = CrashRealmHelper.INSTANCE.mRealm.copyFromRealm(it)
                list.add(copyToRealm.toJson())
            }
            try {
                val addCrash = CrashHttpClient.crashApiService.addCrash(list)
                if (addCrash.code == 200) {
                    UIUtils.showToast("上传成功")
                    handleSuccessData()
//                    deletePushAll(queryCrashData)
                } else {
                    UIUtils.showToast("上传失败：${addCrash.msg}")
                }
            } catch (t: Throwable) {
//                UIUtils.showToast("${t.message}")
                LogUtil.e(t.message)
            }


        }
    }

    private fun handleSuccessData() {
        CrashRealmHelper.INSTANCE.mRealm.executeTransaction {
            CrashRealmHelper.INSTANCE.deleteCrashDataIsPush()
        }

    }
}