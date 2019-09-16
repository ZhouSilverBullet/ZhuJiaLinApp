package com.sdxxtop.trackerlibrary.listener;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sdxxtop.trackerlibrary.db.base.DaoHelperImpl;
import com.sdxxtop.trackerlibrary.db.entity.TrackerPathUtil;
import com.sdxxtop.trackerlibrary.util.LogUtil;

import java.util.Map;
import java.util.WeakHashMap;

public class FragmentLifecycleListener extends FragmentManager.FragmentLifecycleCallbacks implements OnFragmentVisibleListener {

    private DaoHelperImpl mDaoHelper = new DaoHelperImpl();

    private Map<Fragment, Long> resumeTimeMap = new WeakHashMap<>();
    private Map<Fragment, String> keyMap = new WeakHashMap<>();

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentAttached(fm, f, f.getActivity());
        resumeTimeMap.put(f, 0L);

        long startTime = System.currentTimeMillis();
        String key = mDaoHelper.getKey(f.getClass().getName(), startTime, f.hashCode());
        keyMap.put(f, key);
        mDaoHelper.createLifecycle(key, startTime);

        if (f instanceof LifecycleFragment) {
            ((LifecycleFragment) f).listener = this;
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        if (!f.isHidden() && f.getUserVisibleHint()) {
            onVisibleChanged(f, true);
        }
        LogUtil.i(f.getClass().getSimpleName() + " onFragmentResumed");
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        if (!f.isHidden() && f.getUserVisibleHint()) {
            onVisibleChanged(f, false);
        }
        // 解决viewpager中fragment切换时事件统计失效的问题
        LogUtil.i(f.getClass().getSimpleName() + " onFragmentPaused");
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);

        LogUtil.i(f.getClass().getSimpleName() + " onFragmentStopped");
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);

        mDaoHelper.onStopOrDestroyLifecycle(keyMap.get(f), System.currentTimeMillis());

        resumeTimeMap.remove(f);

        LogUtil.i(f.getClass().getSimpleName() + " onFragmentDetached");
    }

    @Override
    public void onVisibleChanged(Fragment f, boolean visible) {
        if (visible) {
            //用户看到了fragment
            resumeTimeMap.put(f, System.currentTimeMillis());
        } else {
            //用户已经看不到fragment
            long currentTimeMillis = System.currentTimeMillis();
            long currentDuration = (currentTimeMillis - resumeTimeMap.get(f));

            String key = keyMap.get(f);
            mDaoHelper.onPauseLifecycle(key, TrackerPathUtil.generateViewPath(f.getContext(), f), currentDuration, currentTimeMillis);
        }

        LogUtil.i(f.getClass().getSimpleName() + " Visible is " + visible);
    }
}
