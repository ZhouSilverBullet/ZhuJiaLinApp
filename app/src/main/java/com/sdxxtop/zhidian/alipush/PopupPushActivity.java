package com.sdxxtop.zhidian.alipush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.google.gson.Gson;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.push.PushCenterActivity;

import java.util.Map;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-12 16:50
 * Version: 1.0
 * Description:
 */
public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = getIntent();
//        String title = intent.getStringExtra("title");
//        String summary = intent.getStringExtra("summary");
//        ToastUtil.show("OnMiPushSysNoticeOpened, title: " + title + ", content: " + summary);
    }

    /**
     * 实现通知打开回调方法，获取通知相关信息
     *
     * @param title   标题
     * @param summary 内容
     * @param extMap  额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
//        ToastUtil.show("OnMiPushSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);
        Log.e(TAG, "OnMiPushSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);

        PushCenterActivity.startActivityToReceiver(this, new Gson().toJson(extMap));
        finish();
    }
}