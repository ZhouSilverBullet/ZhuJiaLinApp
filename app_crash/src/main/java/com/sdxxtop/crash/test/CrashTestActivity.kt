package com.sdxxtop.crash.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sdxxtop.crash.push.PushData
import com.sdxxtop.crash.R
import com.sdxxtop.crash.data.CrashData
import com.sdxxtop.crash.db.CrashRealmHelper
import kotlinx.coroutines.MainScope
import java.text.SimpleDateFormat
import java.util.*

class CrashTestActivity : AppCompatActivity() {

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA)
    val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_test)
    }

    fun saveCrash(v: View) {
        val crashData = CrashData()
        crashData.userid = "5555555"
        crashData.crash_info = "测试"
//        crashData.crash_info = "VivoPush.StatMsgStore: android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: ONLINE_MSG_TABLE.event_id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)\n" +
//                "        at android.database.sqlite.SQLiteConnection.nativeExecuteForLastInsertedRowId(Native Method)\n" +
//                "        at android.database.sqlite.SQLiteConnection.executeForLastInsertedRowId(SQLiteConnection.java:829)\n" +
//                "        at android.database.sqlite.SQLiteSession.executeForLastInsertedRowId(SQLiteSession.java:788)\n" +
//                "        at android.database.sqlite.SQLiteStatement.executeInsert(SQLiteStatement.java:86)\n" +
//                "        at android.database.sqlite.SQLiteDatabase.insertWithOnConflict(SQLiteDatabase.java:1564)\n" +
//                "        at android.database.sqlite.SQLiteDatabase.insertOrThrow(SQLiteDatabase.java:1459)\n" +
//                "        at com.vivo.push.stat.a.d.a.c(StatMsgStore.java:155)\n" +
//                "        at com.vivo.push.stat.a.c.a.a(OnlineMsgHandler.java:51)\n" +
//                "        at com.vivo.push.stat.a.b.b(ReportTaskSyncWorker.java:1244)\n" +
//                "        at com.vivo.push.al\$a.handleMessage(Worker.java:65)\n" +
//                "        at android.os.Handler.dispatchMessage(Handler.java:106)\n" +
//                "        at android.os.Looper.loop(Looper.java:224)\n" +
//                "        at android.os.HandlerThread.run(HandlerThread.java:65)"
        crashData.crash_time = sdf.format(Calendar.getInstance().time)
        CrashRealmHelper.INSTANCE.installCrashData(crashData)
    }

    fun readCrash(v: View) {

        PushData.pushData()
//        showToasty(queryCrashData)
//        mainScope.launch {
//            val queryCrashData = CrashRealmHelper.INSTANCE.queryCrashDataIsNotPush()
//            val list = ArrayList<String>()
//            queryCrashData.forEach {
//                val copyToRealm = CrashRealmHelper.INSTANCE.mRealm.copyFromRealm(it)
//                list.add(GSON.toJson(copyToRealm))
//            }
//            try {
//                val addCrash = CrashHttpClient.crashApiService.addCrash(list)
//                if (addCrash.code == 200) {
//                    showToasty("上传成功")
//                } else {
//                    showToasty("上传失败：${addCrash.msg}")
//                }
//            } catch (t: Throwable) {
//                showToasty("${t.message}")
//                Log.e("CrashTestActivity", t.message)
//            }
//
//        }
    }

    fun deleteCrash(v: View) {
        CrashRealmHelper.INSTANCE.deleteCrashDataIsPush()
    }
}
