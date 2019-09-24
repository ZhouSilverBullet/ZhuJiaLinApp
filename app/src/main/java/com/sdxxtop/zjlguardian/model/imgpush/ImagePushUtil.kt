package com.sdxxtop.zjlguardian.model.imgpush

import com.amap.api.mapcore.util.it
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpImageParams
import java.io.File

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 19:07
 * Version: 1.0
 * Description:
 */
class ImagePushUtil(val imagePushPath: MutableList<File>, val strImgList: ArrayList<String> = ArrayList()) : BaseViewModel() {


    fun pushImage(successBlock: (ArrayList<String>) -> Unit,
                  failBlock: (code: Int, msg: String, t: Throwable) -> Unit) {

//        for (i in 0..imagePushPath.size-1) {
//            pushOneImage(imagePushPath[i], successBlock, failBlock)
//        }

        loadOnUI({
            val params = HttpImageParams()
            params.addImagePathList("file[]", imagePushPath)

            RetrofitClient.apiService.postUpimg(params.imgData)
        }, {
            //                strImgList.add(it.img)
            successBlock(it.img)
        }, { code, msg, t ->
            failBlock(code, msg, t)
        })

    }

    private fun pushOneImage(it: File, successBlock: (ArrayList<String>) -> Unit,
                             failBlock: (code: Int, msg: String, t: Throwable) -> Unit) {
        loadOnUI({
            val params = HttpImageParams()
            val arrayList = ArrayList<File>()
            arrayList.add(it)
            params.addImagePathList("file", arrayList)

            RetrofitClient.apiService.postUpimg(params.imgData)
        }, {
            //            strImgList.add(it.img)
            if (strImgList.size == imagePushPath.size) {
                successBlock(it.img)
            }
        }, { code, msg, t ->
            failBlock(code, msg, t)
        })
    }
}