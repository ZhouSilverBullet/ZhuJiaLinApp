package com.sdxxtop.trackerlibrary.db.base;


import androidx.core.util.Pair;

import com.sdxxtop.trackerlibrary.Tracker;
import com.sdxxtop.trackerlibrary.db.TrackerPathEntryDao;
import com.sdxxtop.trackerlibrary.db.control.DaoManager;
import com.sdxxtop.trackerlibrary.db.entity.TrackerPathEntry;
import com.sdxxtop.trackerlibrary.util.LogUtil;

import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-17 10:20
 * Version: 1.0
 * Description:
 */
public abstract class BaseDaoHelper {
    private static final String TAG = "DaoHelper";

    private void insertOrReplace(TrackerPathEntry entry) {
        getPathDao().insertOrReplace(entry);

        if (entry.getIsComplete()) {
            LogUtil.d(TAG, entry.getKey() + "完整的数据 插入成功！！");
        } else {
            LogUtil.d(TAG, entry.getKey() + "插入成功！！");
        }
    }

    private TrackerPathEntry query(String key, String userId, String companyId) {
        List<TrackerPathEntry> list = getPathDao().queryBuilder()
                .where(TrackerPathEntryDao.Properties.Key.eq(key))
                .where(TrackerPathEntryDao.Properties.UserId.eq(userId))
                .where(TrackerPathEntryDao.Properties.CompanyId.eq(companyId))
                .build()
                .list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 创建的时候，保存数据
     *
     * @param key
     * @param startTime
     */
    public void createLifecycle(String key, Long startTime) {
        DaoManager.getInstance().getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {

                TrackerPathEntry trackerPathEntry = new TrackerPathEntry();
                try {
                    List<Pair<String, String>> call = Tracker.getInstance().getCallback().call();
                    trackerPathEntry.setUserId(call.get(0).second);
                    trackerPathEntry.setCompanyId(call.get(1).second);
                } catch (Exception e) {
                    e.printStackTrace();

                    trackerPathEntry.setUserId("");
                    trackerPathEntry.setCompanyId("");
                }

                trackerPathEntry.setKey(key);
                trackerPathEntry.setStartTime(startTime);
                trackerPathEntry.setIsComplete(false);
                insertOrReplace(trackerPathEntry);
            }
        });

    }

    /**
     * 消失不见的时候，保存不见的时候的数据
     * 或者  更新数据库的时间
     *
     * @param key
     * @param duration 时间差
     * @param endTime  结束时间
     */
    public void onPauseLifecycle(String key, String path, Long duration, Long endTime) {
        DaoManager.getInstance().getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                String userId = "";
                String companyId = "";
                try {
                    List<Pair<String, String>> call = Tracker.getInstance().getCallback().call();
                    userId = call.get(0).second;
                    companyId = call.get(1).second;
                } catch (Exception e) {
                    LogUtil.e(TAG, "call 接口中出现了错误！！");
                    e.printStackTrace();
                }

                TrackerPathEntry trackerPathEntry = query(key, userId, companyId);
                if (trackerPathEntry == null) {
                    LogUtil.e(TAG, "数据库获取数据失败！！");
                    return;
                }

                LogUtil.i(TAG, "读取出来的：" + trackerPathEntry);

                trackerPathEntry.setIsComplete(false);
                //和上次的时间相加
                Long beforeDuration = trackerPathEntry.getDuration();
                beforeDuration = beforeDuration == null ? 0L : beforeDuration;
                trackerPathEntry.setDuration(duration + beforeDuration);

                trackerPathEntry.setEndTime(endTime);
                LogUtil.i(TAG, "改变之后的数据：" + trackerPathEntry);

                //设置路径，因为ActivityLifecycleCallbacks调取的时候，还没获取到值
                trackerPathEntry.setPath(path);
                insertOrReplace(trackerPathEntry);
            }
        });
    }

    /**
     * 在stop/destroy生命周期的时候进行，
     * 将isComplete 变成true 更新到数据库，变为一个完整的数据库
     */
    public void onStopOrDestroyLifecycle(String key, Long endTime) {
        DaoManager.getInstance().getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                String userId = "";
                String companyId = "";
                try {
                    List<Pair<String, String>> call = Tracker.getInstance().getCallback().call();
                    userId = call.get(0).second;
                    companyId = call.get(1).second;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                TrackerPathEntry trackerPathEntry = query(key, userId, companyId);
                if (trackerPathEntry == null) {
                    LogUtil.e(TAG, "数据库获取数据失败！！");
                    return;
                }
                trackerPathEntry.setIsComplete(true);
                trackerPathEntry.setEndTime(endTime);
                insertOrReplace(trackerPathEntry);
            }
        });
    }


    public TrackerPathEntryDao getPathDao() {
        return DaoManager.getInstance().getDaoSession().getTrackerPathEntryDao();
    }

    /**
     * 用当前的界面名字 + onCreate的时间 + hashcode来做主键
     *
     * @param name
     * @param currentTime
     * @param hashCode
     * @return
     */
    public String getKey(String name, long currentTime, int hashCode) {
        return name + currentTime + hashCode;
    }
}
