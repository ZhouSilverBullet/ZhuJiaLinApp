package com.sdxxtop.trackerlibrary.push;

import android.util.Log;

import com.sdxxtop.trackerlibrary.Tracker;
import com.sdxxtop.trackerlibrary.bean.EventBean;
import com.sdxxtop.trackerlibrary.db.control.DaoManager;
import com.sdxxtop.trackerlibrary.db.entity.TrackerPathEntry;
import com.sdxxtop.trackerlibrary.http.BaseResponse;
import com.sdxxtop.trackerlibrary.http.Params;
import com.sdxxtop.trackerlibrary.http.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-08 17:27
 * Version: 1.0
 * Description:
 */
public class HttpHelper {
    private static final String TAG = "HttpHelper";


    public static void pushTrackerEntry(List<TrackerPathEntry> list, boolean test) {
        Params params = new Params();
        params.put("at", 1 /*1.andorid 2.ios*/);
        params.put("dt", Tracker.GSON.toJson(list));

        RetrofitClient.getTrackApiService().postEventData(Tracker.getInstance().getUploadUrl(), params.getData()).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null) {
                    if (response.body().code == 200) {
                        if (test) {
                            pushDataHasTrue(list);
                        } else {
                            pushSuccess(list);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private static void pushDataHasTrue(List<TrackerPathEntry> list) {
        for (TrackerPathEntry trackerPathEntry : list) {
            trackerPathEntry.setIsPush(true);
            trackerPathEntry.setIsComplete(true);
        }

        DaoManager.getInstance()
                .getDaoSession()
                .getTrackerPathEntryDao()
                .insertOrReplaceInTx(list);
//        //如果上传成功就,在test的环境下面，把push置为true
//        RealmHelper.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                for (EventBean realmEventBean : realmEventBeans) {
//                    realmEventBean.isPush = true;
//                }
//            }
//        });
    }

    private static void pushSuccess(List<TrackerPathEntry> list) {
        DaoManager.getInstance()
                .getDaoSession()
                .getTrackerPathEntryDao()
                .deleteInTx(list);
//        //如果上传成功就删除本地的操作
//        RealmHelper.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RealmHelper.getInstance().deleteAllEventData();
//            }
//        });
    }

}
