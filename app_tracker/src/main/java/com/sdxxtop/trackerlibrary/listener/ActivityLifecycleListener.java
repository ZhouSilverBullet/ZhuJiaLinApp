package com.sdxxtop.trackerlibrary.listener;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.sdxxtop.trackerlibrary.db.base.BaseDaoHelper;
import com.sdxxtop.trackerlibrary.db.entity.TrackerPathUtil;
import com.sdxxtop.trackerlibrary.util.LogUtil;

import java.util.Map;
import java.util.WeakHashMap;

public class ActivityLifecycleListener extends BaseDaoHelper implements Application.ActivityLifecycleCallbacks {

    private Map<Context, Long> resumeTimeMap = new WeakHashMap<>();
    private Map<Context, FragmentLifecycleListener> listenerMap = new WeakHashMap<>();

    private Map<Context, String> keyMap = new WeakHashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

        long startTime = System.currentTimeMillis();
        String key = getKey(activity.getClass().getName(), startTime, activity.hashCode());
        keyMap.put(activity, key);
        createLifecycle(key, startTime);

        registerFragmentLifecycleListener(activity);


    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        resumeTimeMap.put(activity, System.currentTimeMillis());

        LogUtil.i(activity.getClass().getSimpleName() + " onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //这一次用户使用的时间
        long currentTimeMillis = System.currentTimeMillis();
        long currentDuration = (currentTimeMillis - resumeTimeMap.get(activity));

        String key = keyMap.get(activity);
        onPauseLifecycle(key, TrackerPathUtil.generateViewPath(activity, null), currentDuration, currentTimeMillis);

        LogUtil.i(activity.getClass().getSimpleName() + " onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.i(activity.getClass().getSimpleName() + " onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        onStopOrDestroyLifecycle(keyMap.get(activity), System.currentTimeMillis());

        resumeTimeMap.remove(activity);

        // 注销之后Fragment onStop之后的生命周期将不再执行
        // unregisterFragmentLifecycleListener(activity);
        LogUtil.i(activity.getClass().getSimpleName() + " onActivityDestroyed");
    }

    private void registerFragmentLifecycleListener(final Activity context) {
        if (context instanceof FragmentActivity) {
            FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentLifecycleListener listener = new FragmentLifecycleListener();
            listenerMap.put(context, listener);
            fm.registerFragmentLifecycleCallbacks(listener, true);
        }
    }

    private void unregisterFragmentLifecycleListener(final Activity context) {
        FragmentLifecycleListener listener = listenerMap.get(context);
        if (listener != null) {
            ((FragmentActivity) context).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(listener);
            listenerMap.remove(context);
        }
    }
}
