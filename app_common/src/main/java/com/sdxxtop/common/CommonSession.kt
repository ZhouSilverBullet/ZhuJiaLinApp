package com.sdxxtop.common

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import java.lang.ref.WeakReference

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-30 09:01
 * Version: 1.0
 * Description:
 */
object CommonSession {
    private lateinit var contextDef: WeakReference<Context>
    lateinit var AUTHORITY: String;

    /**
     * authority -> FileProvider 中的字符串
     */
    fun initCommon(context: Context, authority: String, isDebug: Boolean, logTag: String = "${context.packageName}-tag") {
        this@CommonSession.contextDef = WeakReference(context)
        this@CommonSession.AUTHORITY = authority

        initLogger(isDebug, logTag)
    }

    @JvmStatic
    fun getContext(): Context {
        if (contextDef.get() == null) {
            throw IllegalAccessException("CommonSession需要被初始化，用于获取资源等...")
        }
        return contextDef.get()!!
    }

    //初始化logger
    private fun initLogger(isDebug: Boolean, logTag: String) {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)  // （可选）要显示的方法行数。 默认2
                .methodOffset(7)// （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                //.logStrategy(new LogcatLogStrategy())  //（可选）更改要打印的日志策略。 默认LogCat
                .tag(logTag) //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        //设置日志在debug模式下才进行显示
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return isDebug
            }
        })
    }

}