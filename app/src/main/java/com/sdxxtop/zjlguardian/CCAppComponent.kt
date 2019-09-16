package com.sdxxtop.zjlguardian

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.sdxxtop.zjlguardian.ui.MainActivity


/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-29 17:35
 * Version: 1.0
 * Description:
 */
class CCAppComponent : IComponent {
    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            "MainActivity" -> {
                CCUtil.navigateTo(cc, MainActivity::class.java)
                //返回处理结果给调用方
                CC.sendCCResult(cc.callId, CCResult.success())
            }
            else -> {
            }
        }
        return false
    }

    override fun getName(): String {
        return "CCAppComponent"
    }

}