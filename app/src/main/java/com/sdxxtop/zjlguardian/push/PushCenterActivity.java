package com.sdxxtop.zjlguardian.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity;
import com.sdxxtop.zjlguardian.ui.message.MessageDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/6/15.
 * 处理多个
 */

public class PushCenterActivity extends AppCompatActivity {
    private static final String TAG = "PushCenterActivity";

    private String extraMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        EventBus.getDefault().register(this);
        initVariables();
    }

    protected void initVariables() {
        if (getIntent() != null) {
            extraMap = getIntent().getStringExtra("extraMap");
            if (!TextUtils.isEmpty(extraMap)) {
                startToAc(extraMap);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            extraMap = intent.getStringExtra("extraMap");
            if (!TextUtils.isEmpty(extraMap)) {
                startToAc(extraMap);
            } else {
                finish();
            }
        }
    }

    private void startToAc(String extraMap) {
        try {
            JSONObject jsonObject = new JSONObject(extraMap);

            if (!jsonObject.has("main_id")) {
                Log.e(TAG, "main_Id 不存在！！！！！");
                finish();
                return;
            }

            if (!jsonObject.has("type")) {
                return;
            }

            int type = jsonObject.optInt("type");
            int mainId = jsonObject.optInt("main_id");
            Intent intent = null;
            // 0：普通消息 1咨询 2投诉 3上报事件 4自行处理
            switch (type) {

                // 0 普通消息
                case 0:
                    intent = new Intent(this, MessageDetailActivity.class);
                    intent.putExtra("messageId", mainId);
                    startActivity(intent);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    intent = new Intent(this, EventReportDetailActivity.class);
                    if(type == 3) {
                        intent.putExtra(EventReportDetailActivity.Companion.getKEY_EVENT_TYPE(), EventReportDetailActivity.Companion.getTYPE_EVENT());
                        intent.putExtra("eventId", mainId);
                        startActivity(intent);
                    } else {
                        intent.putExtra(EventReportDetailActivity.Companion.getKEY_EVENT_TYPE(), EventReportDetailActivity.Companion.getTYPE_COMMISSION());
                        intent.putExtra(EventReportDetailActivity.Companion.getREQUEST_TYPE(), type);
                        intent.putExtra(EventReportDetailActivity.Companion.getIS_COMMISSION(), true);
                        intent.putExtra("eventId", mainId);
                        startActivity(intent);
                    }
                    break;

                default:
                    break;

            }
            finish();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            finish();
        }
    }

//    @Subscribe
//    public void pushCenterEvent(PushCenterEvent event) {
//        finish();
//    }

    public static void startActivityToReceiver(final Context context, String extraMap) {
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), PushCenterActivity.class.getName());
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra("extraMap", extraMap);
        context.startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
