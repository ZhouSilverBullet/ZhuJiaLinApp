package com.sdxxtop.zjlguardian.ui.report.viewmodel

import android.graphics.Bitmap
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.format.draw.BitmapDrawFormat
import com.bin.david.form.data.table.TableData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.internal.LinkedTreeMap
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.dialog.IosAlertDialog
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.report.data.ReportFromData
import java.util.*
import kotlin.collections.ArrayList

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-23 11:32
 * Version: 1.0
 * Description:
 */
class ReportMessageDataViewModel : BaseViewModel() {

    var formId = 0

    val mSearchShowData = MutableLiveData<Boolean>()
    val mReportList = MutableLiveData<Any>()

    //删除
    val mDelSuccess = MutableLiveData<Boolean>()


    fun showSearchEdit(v: View) {
//        isSearchShow.set(true)
        mSearchShowData.value = true

    }

    fun searchData(searchValue: String) {
        loadOnUI({
            val params = HttpParams()
            params.put("fi", formId)
            if (!TextUtils.isEmpty(searchValue)) {
                params.put("sh", searchValue)
                RetrofitClient.apiService.postEventCollectSearch(params.data)
            } else {
                RetrofitClient.apiService.postEventCollectFormData(params.data)
            }
        }, {
            showLoadingDialog(false)
            Log.e("ReportViewModel", it.toString())
//            mReportList.value = it.list
            mReportList.value = it

        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }

    fun handleTableData(fragment: Fragment, stTable: SmartTable<ReportFromData>, any: Any) {
        if (any == null) {
            return
        }

        //用于图片选择field
        val fileField = getFileField(any)
        //列表的field
        val listField = getListField(any)

        //value1 value2 ... 是图片的情况下
        val tempFileField = ArrayList<String>()

        val columns = getColumns(fragment, stTable, any, listField, fileField, tempFileField)
        listField.add("data_id")
        //这个内容要取出data_id，而listField里面没有这个，需要自己加上去
        val testData = getTestData(any, listField)
        listField.removeAt(listField.size - 1)

        val tableData = TableData("标题测试", testData, columns)
        tableData.setOnRowClickListener { column: Column<Any>, userInfo: ReportFromData, col: Int, row: Int ->
            //            UIUtils.showToast("aaa->$userInfo")

//            UIUtils.showToast("aaa->$userInfo")

            val declaredField = userInfo.javaClass.getDeclaredField(column.fieldName)
            declaredField.isAccessible = true
            val value = declaredField.get(userInfo)
//            if (tempFileField.contains(column.fieldName)) {
//                UIUtils.showToast("图片->$value")
//            } else {
//                UIUtils.showToast("值->$value")
//            }
            showDeleteDialog(fragment.activity, userInfo.dataId)
            Log.e("ReportMessageViewModel", "aaa->$userInfo")
        }

        stTable.setTableData(tableData)

    }

    private fun showDeleteDialog(activity: FragmentActivity?, dataId: Int?) {
        if (activity == null || activity.isFinishing()) {
            return
        }
        IosAlertDialog(activity)
                .builder()
                .setMsg("是否删除该条目")
                .setPositiveButton("确定") {
                    delete(dataId)
                }
                .setNegativeButton("取消") {
                    //填写事件
                }.show()
    }

    private fun getFileField(any: Any): ArrayList<String> {
        val arrayList = (any as LinkedTreeMap<String, ArrayList<Any>>)["file_field"]
        return if (arrayList == null) ArrayList<String>() else (arrayList as ArrayList<String>)
    }

    private val map = HashMap<String, Bitmap>()

    private fun getColumns(fragment: Fragment, stTable: SmartTable<ReportFromData>, any: Any, listField: java.util.ArrayList<String>, fieldField: ArrayList<String>, tempFileField: ArrayList<String>): java.util.ArrayList<Column<*>> {
        val columns = java.util.ArrayList<Column<*>>()
        val arrayList = (any as LinkedTreeMap<String, ArrayList<Any>>)["list"] as ArrayList<Any>
        val itemTreeMap = arrayList[0] as LinkedTreeMap<String, Any>

        for (i in 0 until itemTreeMap.size) {
            val fieldName = listField[i]
            val anyField = itemTreeMap[fieldName]
            if (anyField is String) {
                val tempFileName = "value$i"
                if (fieldField.contains(fieldName)) {
                    //添加了，好到时图片的时候去取出来判断，是不是图片
                    tempFileField.add(tempFileName)
                    val avatarColumn = Column(anyField, tempFileName, object : BitmapDrawFormat<ArrayList<String>>(UIUtils.dip2px(50), UIUtils.dip2px(50)) {
                        override fun getBitmap(arrayList: ArrayList<String>, value: String, position: Int): Bitmap? {
                            if (arrayList == null || arrayList.size == 0) {
                                return null
                            }
                            if (map.get(arrayList[0]) == null) {
                                Glide.with(fragment).asBitmap().load(arrayList[0])
                                        .apply(bitmapTransform(CenterCrop())).into(object : SimpleTarget<Bitmap>() {
                                            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                                                map.put(arrayList[0], bitmap)
                                                stTable.invalidate()
                                            }
                                        })
                            }
                            val bitmap = map[arrayList[0]]

                            return bitmap
                        }
                    })
                    columns.add(avatarColumn)
                } else {
                    columns.add(Column<Any>(anyField, tempFileName))
                }
            }
        }

        return columns
    }

    private fun getTestData(any: Any, listField: ArrayList<String>): ArrayList<ReportFromData> {
        val list = ArrayList<ReportFromData>()
        val arrayList = (any as LinkedTreeMap<String, ArrayList<Any>>)["list"] as ArrayList<Any>
        for (i in 0 until arrayList.size - 1) {
            val itemTreeMap = arrayList[i + 1]
                    as LinkedTreeMap<String, Any>
            val reportFromData = ReportFromData()
            for (j in 0 until itemTreeMap.size) {
                val fieldName = listField[j]
                val anyField = itemTreeMap[fieldName]

                if ("data_id" == fieldName) {

                    val dataIdField = reportFromData.javaClass.getDeclaredField("dataId")
                    dataIdField.isAccessible = true
                    if (anyField is Double) {
                        dataIdField.set(reportFromData, anyField.toInt())
                    }

                    //跳出这个层循环
                    continue
                }

                if (anyField is String) {
                    val declaredField = reportFromData.javaClass.getDeclaredField("value$j")
                    declaredField.isAccessible = true
                    declaredField.set(reportFromData, anyField)
                }


                if (anyField is Int) {
                    val declaredField = reportFromData.javaClass.getDeclaredField("value$j")
                    declaredField.isAccessible = true
                    declaredField.set(reportFromData, anyField.toString())
                }

                if (anyField is Float) {
                    val declaredField = reportFromData.javaClass.getDeclaredField("value$j")
                    declaredField.isAccessible = true
                    if (anyField.toInt().toFloat() == anyField) {
                        declaredField.set(reportFromData, anyField.toInt().toString())
                    } else {
                        declaredField.set(reportFromData, anyField.toString())
                    }
                }

                if (anyField is Double) {
                    val declaredField = reportFromData.javaClass.getDeclaredField("value$j")
                    declaredField.isAccessible = true
                    if (anyField.toInt().toDouble() == anyField) {
                        declaredField.set(reportFromData, anyField.toInt().toString())
                    } else {
                        declaredField.set(reportFromData, anyField.toString())
                    }
                }

                if (anyField is ArrayList<*>) {
                    val declaredField = reportFromData.javaClass.getDeclaredField("value$j")
                    declaredField.isAccessible = true
                    declaredField.set(reportFromData, anyField)
                }
            }
            list.add(reportFromData)
        }
        return list
    }

    private fun getListField(any: Any): ArrayList<String> {
        val arrayList = (any as LinkedTreeMap<String, ArrayList<Any>>)["list_field"]
        return if (arrayList == null) ArrayList<String>() else (arrayList as ArrayList<String>)
    }


    fun delete(dataId: Int?) {
        loadOnUI({
            val params = HttpParams()
            params.put("di", dataId ?: 0)
            RetrofitClient.apiService.postEventCollectDel(params.data)
        }, {
            showLoadingDialog(false)
//            Log.e("ReportViewModel", it.toString())
//            mReportList.value = it.list
            mDelSuccess.value = true
        }, { code, msg, t ->

            UIUtils.showToast(msg)

            showLoadingDialog(false)
        })
    }
}