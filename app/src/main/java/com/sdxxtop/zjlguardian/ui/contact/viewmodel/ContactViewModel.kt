package com.sdxxtop.zjlguardian.ui.contact.viewmodel

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.model.api.RetrofitClient
import com.sdxxtop.zjlguardian.model.helper.HttpParams
import com.sdxxtop.zjlguardian.ui.contact.ContactSearchActivity
import com.sdxxtop.zjlguardian.ui.contact.data.TYPE_PART_DATA
import com.sdxxtop.zjlguardian.ui.contact.data.TYPE_USER_DATA
import com.sdxxtop.zjlguardian.ui.contact.data.UserData
import com.sdxxtop.zjlguardian.ui.home.MainTabActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 19:55
 * Version: 1.0
 * Description:
 */
class ContactViewModel : BaseViewModel() {
    private val emptyUserDataList = ArrayList<UserData>()
    val mSearchShowData = MutableLiveData<Boolean>()
    val mUserData = MutableLiveData<ArrayList<UserData>>()

    fun skipContact(v: View) {
        val child = (v.parent as FrameLayout).getChildAt(0)

        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(v.context as MainTabActivity, child, "search").toBundle()

        val intent = Intent(v.context, ContactSearchActivity::class.java)
        v.context.startActivity(intent, bundle)
    }

    fun showSearchEdit(v: View) {
//        isSearchShow.set(true)
        mSearchShowData.value = true

    }

    fun searchData(searchValue: String) {
//        showLoadingDialog(true)
        loadOnUI({
            val params = HttpParams()
            val empty = TextUtils.isEmpty(searchValue)
            val path = if (empty) {
                "lists"
            } else {
                params.put("sh", searchValue)
                "search"
            }

            RetrofitClient.apiService.postPhoneLists(path, params.data)
        }, {
            //            mPushSuccessData.value = it

            val list = ArrayList<UserData>()
            for (contactPart in it.list) {
                val partName = contactPart.part_name
                val partId = contactPart.part_id
                val userPart = UserData(0, "", "")
                userPart.type = TYPE_PART_DATA
                userPart.part_name = partName
                list.add(userPart)

                for (user in contactPart.users) {
                    user.part_name = partName
                    user.part_id = partId
                    user.type = TYPE_USER_DATA
                    list.add(user)
                }
            }

            mUserData.value = list

            showLoadingDialog(false)
        }, { code, msg, t ->

            UIUtils.showToast(msg)
            if (code == 201) {
                mUserData.value = emptyUserDataList
            }

            showLoadingDialog(false)
        })
    }
}