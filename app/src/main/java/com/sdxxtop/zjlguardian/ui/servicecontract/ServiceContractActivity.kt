package com.sdxxtop.zjlguardian.ui.servicecontract

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdxxtop.base.BaseNormalActivity
import com.sdxxtop.webview.CommonWebFragment
import com.sdxxtop.webview.basefragment.BaseWebviewFragment
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityServiceContractBinding

/**
 * 服务协议
 */
class ServiceContractActivity : BaseNormalActivity<ActivityServiceContractBinding>() {

    override fun layoutId() = R.layout.activity_service_contract

    override fun initView() {
        val fragment = CommonWebFragment.newInstance("file:///android_asset/deal.html")

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit()
    }

    companion object {
        @JvmStatic
        fun skipServiceContract(context: Context) {
            context.startActivity(Intent(context, ServiceContractActivity::class.java))
        }
    }
}
