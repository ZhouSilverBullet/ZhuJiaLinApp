package com.baidu.idl.face

import android.content.Context
import com.baidu.idl.face.platform.FaceSDKManager

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-31 15:59
 * Version: 1.0
 * Description:
 */

object FaceSession {

    /**
     * 初始化Baidu人脸识别
     */
    fun initFace(context: Context, licenseID: String, licenseFileName: String = "idl-license.face-android") {
//        FaceSDKManager.getInstance().initialize(context, "luozhuang-face-android", "idl-license.face-android")
        FaceSDKManager.getInstance().initialize(context, licenseID, licenseFileName)
    }

}