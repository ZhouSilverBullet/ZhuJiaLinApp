package com.sdxxtop.zjlguardian.ui.event_report.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.GSON
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpImageParams
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.model.imgpush.ImagePushUtil
import com.sdxxtop.zjlguardian.ui.event_report.data.CatData
import java.io.File

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-18 15:54
 * Version: 1.0
 * Description:
 */
class EventReportViewModel : BaseViewModel() {
    val mEventTitle = ObservableField<String>()
    val mClassifyStr = ObservableField<String>()
    val mClassify = ObservableInt()
    val mEventPlace = ObservableField<String>()
    val mEventDate = ObservableField<String>()
    val mDec = ObservableField<String>()

    val mlonLng = ObservableField<String>()

    val mCatClassifyData = MutableLiveData<List<CatData>>()

    val mPushSuccessData = MutableLiveData<String>()

    fun pushData(imagePushPath: MutableList<File>) {



        val eventTitle = mEventTitle.get() ?: ""
        val eventPlace = mEventPlace.get() ?: ""
        val eventDate = mEventDate.get() ?: ""
        val dec = mDec.get() ?: ""

        if (eventTitle.isEmpty()) {
            UIUtils.showToast("请填写事件的标题")
            return
        }

        if (mClassify.get() == 0) {
            UIUtils.showToast("请选择事件的分类")
            return
        }
        if (eventPlace.isEmpty()) {
            UIUtils.showToast("请选择事件的地点")
            return
        }
        if (eventDate.isEmpty()) {
            UIUtils.showToast("请选择事件的日期")
            return
        }
        if (dec.isEmpty()) {
            UIUtils.showToast("请填写事件的描述")
            return
        }

//        if (imagePushPath.size == 0) {
//            UIUtils.showToast("请填写现场的照片")
//            return
//        }

        //含有图片的时候
//        if (imagePushPath.size > 0) {
//            showLoadingDialog(true)
//            ImagePushUtil(imagePushPath).pushImage({
//                var imgJson = GSON.toJson(it)
//                pushData(eventTitle, eventPlace, eventDate, dec, imagePushPath, imgJson)
//            }, { code: Int, msg: String, t: Throwable ->
//                UIUtils.showToast(msg)
//                showLoadingDialog(false)
//            })
//        } else{
//            showLoadingDialog(true)
//        }
        showLoadingDialog(true)
        pushData(eventTitle, eventPlace, eventDate, dec, imagePushPath, "[]")

    }

    private fun pushData(eventTitle: String, eventPlace: String, eventDate: String, dec: String, imagePushPath: MutableList<File>, imgJson:String) {

        loadOnUI({
            val params = HttpImageParams()
            params.put("te", eventTitle)
            params.put("ci", mClassify.get())
            params.put("pe", eventPlace)
            params.put("sd", eventDate)
            params.put("ct", dec)
//            params.put("ig", imgJson)

            params.addImagePathList("file[]", imagePushPath)

            RetrofitClient.apiService.postEventAdd(params.imgData)
        }, {
            mPushSuccessData.value = it

            showLoadingDialog(false)
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }


    /**
     * 分类类型(1: 咨询 2: 投诉 3：上报、自行处理)
     */
    fun loadClassify(type: Int) {
        loadOnUI({
            val params = HttpParams()
            params.put("ct", type)

            RetrofitClient.apiService.postEventCat(params.data)
        }, {
            mCatClassifyData.value = it.list
        }, { code, msg, t ->

            UIUtils.showToast(msg)

        })
    }
}