package com.olife.o_life.util;

import android.content.Context;
import android.icu.text.IDNA;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.olife.o_life.biz.UserBiz;

import static com.amap.api.mapcore.util.af.a.c;
import static com.amap.api.mapcore.util.af.a.m;

/**
 * 获取当前的位置
 * Created by chenfuhai on 2016/12/16 0016.
 */


public class LocationUtils {

    public interface LocationDoingLisenter {
        void onStart();

        void onSuccess(AMapLocation location);

        void onFailed();

        void onError(LocationDoingError e);

        void onFinish();
    }

    public class LocationDoingError {
        private int code;
        private String message;

        public LocationDoingError(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }


        public String getMessage() {
            return message;
        }
    }


    private static LocationUtils mLocationUtils;
    private Context mContext;

    private LocationUtils(Context context) {
        this.mContext = context;
        init();
    }

    public static LocationUtils getmLocationUtil(Context context) {
        if (mLocationUtils == null) {
            synchronized (LocationUtils.class) {
                if (mLocationUtils == null) {
                    mLocationUtils = new LocationUtils(context);
                }
            }
        }
        return mLocationUtils;
    }

    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;

    private void init() {
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);

        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        mLocationOption.setWifiActiveScan(true);
    }

    public void startLocation(final LocationDoingLisenter lisenter) {


        lisenter.onStart();
        mlocationClient = new AMapLocationClient(mContext);
        //初始化定位参数

        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        lisenter.onSuccess(amapLocation);
                    } else {
                        lisenter.onError(new LocationDoingError(amapLocation.getErrorCode(),
                                amapLocation.getErrorInfo()));
                    }

                } else {
                    lisenter.onFailed();
                }
                mlocationClient.onDestroy();
                lisenter.onFinish();
            }
        });

        mlocationClient.startLocation();



    }

}
