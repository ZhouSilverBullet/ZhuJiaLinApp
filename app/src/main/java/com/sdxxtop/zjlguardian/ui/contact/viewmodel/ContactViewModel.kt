package com.sdxxtop.zjlguardian.ui.contact.viewmodel

import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.sdxxtop.base.BaseViewModel
import com.sdxxtop.zjlguardian.ui.contact.ContactSearchActivity
import com.sdxxtop.zjlguardian.ui.home.MainTabActivity

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-16 19:55
 * Version: 1.0
 * Description:
 */
class ContactViewModel : BaseViewModel() {
//    val isSearchShow = ObservableBoolean()
    val mSearchShowData = MutableLiveData<Boolean>()

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
}