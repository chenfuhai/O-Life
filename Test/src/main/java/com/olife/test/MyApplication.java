package com.olife.test;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by chenfuhai on 2016/12/26 0026.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),"d5f043da11dd213e237c350113217761");

    }
}
