package com.sdxxtop.trackerlibrary.api;

import com.sdxxtop.trackerlibrary.Tracker;
import com.sdxxtop.trackerlibrary.http.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-08 17:20
 * Version: 1.0
 * Description:
 */
public interface TrackApiService {
    String BASE_URL = Tracker.getInstance().getUploadBaseUrl();

    @FormUrlEncoded
    @POST("{path}")
    Call<BaseResponse> postEventData(@Path("path") String path, @Field("data") String data);
}
