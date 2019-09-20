package com.sdxxtop.zjlguardian.widget

import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import com.sdxxtop.ui.callback.SingleClickListener
import com.sdxxtop.zjlguardian.widget.callback.SingleClickCallback

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-19 15:47
 * Version: 1.0
 * Description:
 */
const val SEND_CODE = 100;

class CodeButtonManager(var btn: Button) : Handler.Callback {

    val mHandler: Handler by lazy {
        Handler(this)
    }

    //计数
    var startTime = 60
    private var mBlock: SingleClickCallback? = null

    init {

        btn.text = "发送短信验证码"
        btn.setOnClickListener(object : SingleClickListener(3000) {
            override fun onSingleClick(v: View?) {
                mBlock?.onSingleClick(v!!)
            }

            override fun onFastClick() {
                mBlock?.onFastClick()
            }
        })

    }

    fun startCode() {
        sendHandleMessage(false, startTime)
        btn.isEnabled = false
    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            SEND_CODE -> {
                val time = msg.arg1
                val sb = StringBuilder()
                btn.text = sb.append("验证码发送剩余(").append(time).append(")")

                val lastTime = time - 1
                if (time == 0) {
                    //让btn重新可以使用
                    btn.isEnabled = true
                    btn.text = "发送短信验证码"
                } else {
                    sendHandleMessage(true, lastTime)
                }
            }
            else -> {
            }
        }
        return true
    }

    private fun sendHandleMessage(isDelayed: Boolean, time: Int) {
        val message = Message.obtain()
        message.what = SEND_CODE
        message.arg1 = time
        if (isDelayed) {
            mHandler.sendMessageDelayed(message, 1000)
        } else {
            mHandler.sendMessage(message)
        }
    }

    fun setSingleClick(block: SingleClickCallback) {
        mBlock = block
    }


}