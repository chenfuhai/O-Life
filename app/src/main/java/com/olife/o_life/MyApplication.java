package com.olife.o_life;

import android.app.Application;

import com.amap.api.location.AMapLocationClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.olife.o_life.util.SDcardTools;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * 初始化需要的组件 应用启动最先启动的类
 * Created by chenfuhai on 2016/12/9 0009.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SDcardTools.initialize(getApplicationContext());
        Bmob.initialize(getApplicationContext(), "3cdd8038837f44e97d8fdd566184b979");

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(MyApplication.this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        AMapLocationClient.setApiKey("6524a0aa375b37ae3320db34479ebe6b");

    }
}
