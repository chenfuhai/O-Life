package com.olife.test;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cn.bmob.v3.update.BmobUpdateAgent;

import static android.R.attr.fragment;


public class MainActivity extends BaseActivity {

    private TextView tv ;
    private Handler handler;
    private int progress = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.forceUpdate(this);

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);

        ArrayList<String> fragmentTags = new ArrayList<>();
        fragmentTags.add(Fragment1.class.getName());
        fragmentTags.add(Fragment2.class.getName());
        fragmentTags.add(Fragment3.class.getName());
        fragmentTags.add(Fragment4.class.getName());


        final OlifeFragmentManager fm = OlifeFragmentManager.getInstance(this,R.id.frame);
        fm.addFragments(fragments,fragmentTags,0);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.showFragment(Fragment1.class.getName());
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.showFragment(Fragment2.class.getName());
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.showFragment(Fragment3.class.getName());
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fm.showFragment(Fragment4.class.getName());

                BmobUpdateAgent.forceUpdate(MainActivity.this);
            }
        });



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
