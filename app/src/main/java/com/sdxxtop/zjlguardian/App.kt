package com.sdxxtop.zjlguardian

import android.content.Context
import android.util.Log
import com.billy.cc.core.component.CCUtil.getCurProcessName
import com.sdxxtop.base.BaseApplication
import com.sdxxtop.common.CommonSession
import com.sdxxtop.crash.CrashSession
import com.sdxxtop.mapsdk.MapSession
import com.sdxxtop.network.NetworkSession
import com.sdxxtop.network.utils.SpUtil
import com.sdxxtop.sdk.AnalyticsSession
import com.sdxxtop.trackerlibrary.Tracker
import com.sdxxtop.trackerlibrary.TrackerConfiguration
import java.util.ArrayList
import kotlin.properties.Delegates
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.sdxxtop.alipushsdk.PushSession
import com.sdxxtop.ui.loadsir.*


/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-29 17:41
 * Version: 1.0
 * Description:
 */
class App : BaseApplication() {


    companion object {
        @JvmStatic
        var INSTANCE: Context by Delegates.notNull()
    }

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
                layout.setPrimaryColorsId(R.color.color_F0F0F0, R.color.black_333333)//全局设置主题颜色
                return ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        })
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
                //指定为经典Footer，默认是 BallPulseFooter
                val classicsFooter = ClassicsFooter(context)
                classicsFooter.setBackgroundColor(INSTANCE.getResources().getColor(R.color.color_F0F0F0))
                return classicsFooter.setDrawableSize(20f)
            }
        })
    }



    val TAG = "CCApp"
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        //要把common层进行初始化
        CommonSession.initCommon(this, "$packageName.provider", isDebug())
        //初始化network模块
        NetworkSession.initNetwork(this, BuildConfig.VERSION_CODE)
        //初始化地图
        MapSession.initAMap(this)

//        FaceSession.initFace(this, "licenseID")

        //友盟统计
        AnalyticsSession.initAnalytics(this, isDebug(), "5d4245600cafb2f31f0009f5")

        //crash 初始化
        CrashSession.initCrash(this, isDebug(), BuildConfig.VERSION_NAME)

        Log.i(TAG, "--------------调取-------------->")

        if (isAppProcess(getCurProcessName())) {
//            initTracker()

            LoadSir.beginBuilder()
                    .addCallback(ErrorCallback())
                    .addCallback(EmptyCallback())
                    .addCallback(LoadingCallback())
                    .addCallback(TimeoutCallback())
                    .addCallback(CustomCallback())
                    .setDefaultCallback(LoadingCallback::class.java)
                    .commit()
        }

        PushSession.initPush(this, "2882303761518119728","5381811972728")
    }



    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    fun isAppProcess(process: String): Boolean {
        return packageName.equals(process)
    }

    private fun initTracker() {
        Tracker.getInstance().init(this, TrackerConfiguration()
                .setUploadBaseUrl("http://www.baidu.com/")
                .setUploadUrl("pointlog/burypoint")
                .setAppKey("aaaaaaaaaaaaaaaaaa")
                .setDebug(BuildConfig.DEBUG)
                .setParent(true)
                .setTest(true)
                .setUploadTime(10)
        ) {
            val list = ArrayList<androidx.core.util.Pair<String, String>>()

            val userId = SpUtil.getString("user_id")
            val userIdPair = androidx.core.util.Pair("ui", "1")
            val companyIdPair = androidx.core.util.Pair("ci", "0")
            list.add(userIdPair)
            list.add(companyIdPair)

            list
        }
    }

}