package com.olife.o_life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

/**
 * 欢迎页面
 * Created by cuiliang on 2016/11/26.
 */
public class WelcomeActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent in = new Intent(WelcomeActivity.this,IndexActivity.class);
                startActivity(in);
                finish();
            }
        }).start();
    }
}
