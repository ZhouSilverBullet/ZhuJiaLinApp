package com.sdxxtop.trackerlibrary;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdxxtop.trackerlibrary.db.TrackerPathEntryDao;
import com.sdxxtop.trackerlibrary.db.control.DaoManager;
import com.sdxxtop.trackerlibrary.db.entity.TrackerPathEntry;
import com.sdxxtop.trackerlibrary.listener.ActivityLifecycleListener;
import com.sdxxtop.trackerlibrary.listener.IPushParamCallback;
import com.sdxxtop.trackerlibrary.push.HttpHelper;
import com.sdxxtop.trackerlibrary.util.LogUtil;

import java.util.List;

import io.realm.Realm;

public class Tracker {
    private static final String TAG = "Tracker";

    //json的时候只进行把带有Expose字段的注解，进行变成json
    public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    private Context context;
    private TrackerConfiguration config;

    private final int UPLOAD_EVENT_WHAT = 0xff01;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPLOAD_EVENT_WHAT) {
                uploadEventInfo(msg.arg1 == 1);
                handler.sendEmptyMessageDelayed(UPLOAD_EVENT_WHAT, config.getUploadTime() * 1000);
            }
        }
    };
    private IPushParamCallback<String> mCallback;

    private Tracker() {

    }

    public static Tracker getInstance() {
        return Singleton.instance;
    }

    public void init(Application context, TrackerConfiguration config, IPushParamCallback<String> callback) {
        if (config == null) {
            throw new IllegalArgumentException("config can't be null");
        }

        this.context = context;
        mCallback = callback;
        Realm.init(context);
        DaoManager.getInstance().init(context);
        setTrackerConfig(config);
        //注册activity的生命周期
        context.registerActivityLifecycleCallbacks(new ActivityLifecycleListener());

        startRequestConfig();
    }

    private void setTrackerConfig(TrackerConfiguration config) {
        if (config != null) {
            this.config = config;
        }
    }

    /**
     * 从服务器请求埋点的配置信息
     */
    private void startRequestConfig() {
        Message message = Message.obtain();
        message.what = UPLOAD_EVENT_WHAT;
        //这个1表示初始化的开始第一次请求
        message.arg1 = 1;
        handler.sendMessage(message);
    }


    /**
     * 上传埋点数据
     */
    private synchronized void uploadEventInfo(boolean isFirst) {
        asyncPush(isFirst);
    }

    private void asyncPush(boolean isFirst) {
        DaoManager.getInstance().getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<TrackerPathEntry> list = null;
                if (isFirst) {
                    list = DaoManager.getInstance()
                            .getDaoSession()
                            .getTrackerPathEntryDao()
                            .queryBuilder()
                            .where(TrackerPathEntryDao.Properties.IsPush.eq(false))
                            //启动页的时候，会有没有统计到 时间的，所以要排除
                            .where(TrackerPathEntryDao.Properties.Duration.gt(0L))
                            .build()
                            .list();
                } else {
                    list = DaoManager.getInstance()
                            .getDaoSession()
                            .getTrackerPathEntryDao()
                            .queryBuilder()
                            .where(TrackerPathEntryDao.Properties.IsComplete.eq(true))
                            .where(TrackerPathEntryDao.Properties.IsPush.eq(false))
                            .where(TrackerPathEntryDao.Properties.Duration.gt(0L))
                            .build()
                            .list();
                }

                if (list == null || list.size() == 0) {
                    LogUtil.w(TAG, "数据为空，不进行上传");
                    return;
                }

                HttpHelper.pushTrackerEntry(list, config.isTest());
            }
        });
    }

    public Context getContext() {
        return context;
    }


    public static class Singleton {
        private final static Tracker instance = new Tracker();
    }

    public IPushParamCallback<String> getCallback() {
        return mCallback;
    }

    //////// 获取versionCode ///////

    private String versionCode;

    private void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionCode() {
        if (!TextUtils.isEmpty(versionCode)) {
            return versionCode;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionCode = String.valueOf(pi.versionCode);
            setVersionCode(versionCode);
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return "10001";
        }
    }

    public String getAppKey() {
        return config.getAppKey();
    }

    public String getUploadBaseUrl() {
        return config.getUploadBaseUrl();
    }

    public String getUploadUrl() {
        return config.getUploadUrl();
    }

    public boolean isDebug() {
        return config.isDebug();
    }

    private boolean isTest() {
        return config.isTest();
    }
}
