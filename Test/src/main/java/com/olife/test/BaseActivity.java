package com.olife.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;


/**
 * Created by chenfuhai on 2016/12/5 0005.
 */

public  abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//1
        setContentView(getLayoutId());//在这里面吧子类的layout获得 子类就不需要 setContentView了

        //通过代码的方式 避免重复
        //在每个布局文件中都要写上 android:fitsSystemWindows="true"

        ViewGroup contentLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        if (contentLayout.getChildAt(0)!=null){
            contentLayout.getChildAt(0).setFitsSystemWindows(true);
        }


    }

    /**
     * 在这里面吧子类的layout获得 子类就不需要 setContentView了
     * <br/> BaseActivity进行其他处理0
     * */
    abstract protected int getLayoutId();

}
