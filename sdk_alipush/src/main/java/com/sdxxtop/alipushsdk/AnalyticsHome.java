package com.sdxxtop.alipushsdk;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.sdxxtop.common.utils.LogUtil;

/**
 * Created by Administrator on 2018/5/23.
 */

public class AnalyticsHome {
    public static final String TAG = "AnalyticsHome";
    private static CloudPushService pushService;

    private static void init() {
        if (pushService == null) {
            pushService = PushServiceFactory.getCloudPushService();
        }
    }

    public static void bindAccount(final String userId) {
        init();
        pushService.unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) { //先解除绑定
                LogUtil.e(TAG, "unbindAccount s= " + s);
                bind(userId);
            }

            @Override
            public void onFailed(String s, String s1) {
                bind(userId);
                LogUtil.e(TAG, " onFailed bindAccount s= " + s);
                LogUtil.e(TAG, " onFailed bindAccount s1= " + s1);
            }
        });


    }

   private static void bind(final String userId) {
        pushService.bindAccount(userId, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtil.e(TAG, "unbindAccount s= " + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                LogUtil.e(TAG, " onFailed unbindAccount s= " + s);
                LogUtil.e(TAG, " onFailed unbindAccount s1= " + s1);
            }
        });
    }

    public static void unbindAccount() {
        init();
        pushService.unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtil.e(TAG, "bindAccount s= " + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                LogUtil.e(TAG, " onFailed bindAccount s= " + s);
                LogUtil.e(TAG, " onFailed bindAccount s1= " + s1);
            }
        });

    }
}
