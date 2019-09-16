package com.sdxxtop.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import org.apache.http.conn.ConnectTimeoutException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-25 14:41
 * Version: 1.0
 * Description:
 */

object CommonNetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val systemService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = systemService.activeNetworkInfo
        return activeNetworkInfo?.isAvailable ?: false
    }
}