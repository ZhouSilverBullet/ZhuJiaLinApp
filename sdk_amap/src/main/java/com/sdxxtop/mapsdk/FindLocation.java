package com.sdxxtop.mapsdk;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.common.utils.UIUtils;

import io.reactivex.functions.Consumer;

/**
 * 用于定位获取金纬度
 */
public class FindLocation implements AMapLocationListener {
    private String TAG = "AMapFindLocation";

    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;

    public static FindLocation getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final FindLocation INSTANCE = new FindLocation();
    }

    private FindLocation() {
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        Log.e(TAG, "onLocationChanged: amapLocation.getLocationType() = " + amapLocation.getLocationType() + " -- "
                + amapLocation.getLatitude() + ", " + amapLocation.getLongitude());
        try {
            if (locationListener != null) {
                locationListener.onAddress(amapLocation);
            }
        } finally {
            locationListener = null;
            stopLocation();
        }

    }

    public void location(Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        location(rxPermissions);
    }

    public void location(RxPermissions rxPermissions) {
        if (rxPermissions == null) {
            return;
        }

        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    location();
                } else {
                    UIUtils.showToast("请开启定位权限再进行尝试");
                }
            }
        });
    }

    private void location() {


        mlocationClient = new AMapLocationClient(MapSession.getContext());

        //声明mLocationOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationOption.setWifiScan(true);
        mLocationOption.setLocationCacheEnable(false);

        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        mlocationClient.startLocation();
    }

    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
        }
    }

    public interface LocationListener {
        void onAddress(AMapLocation address);
    }

    private LocationListener locationListener;

    public void setLocationListener(LocationListener listener) {
        this.locationListener = listener;
    }

    public void removeLocationListener() {
        locationListener = null;
    }


}
