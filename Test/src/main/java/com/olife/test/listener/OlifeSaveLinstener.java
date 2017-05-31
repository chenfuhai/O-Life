package com.olife.test.listener;

import com.olife.test.OlifeError;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by chenfuhai on 2017/5/31 0031.
 */

public abstract class OlifeSaveLinstener<T> {
    public  OlifeSaveLinstener(){

    }
    public abstract void done(T var1, OlifeError e);

}
