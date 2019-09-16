package com.sdxxtop.trackerlibrary.http;

import com.sdxxtop.trackerlibrary.api.TrackApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-08-08 17:19
 * Version: 1.0
 * Description:
 */
public class RetrofitClient {
    private static Retrofit retrofit;


    private static Retrofit getRetrofit() {
        if (retrofit != null) {
            return retrofit;
        }
        return retrofit = new Retrofit.Builder()
                .baseUrl(TrackApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static TrackApiService getTrackApiService() {
        return getRetrofit().create(TrackApiService.class);

    }

}
