package com.olife.o_life;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.olife.o_life.adapter.OneKeyRecyclerAdapter;
import com.olife.o_life.entity.OnekeyResultLocal;
import com.olife.pointview.PointViewFull;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;


/**
 * 一件检测的activity版本
 */
public class OneKeyCheckActivity extends ToolBarBaseActivity {

    private OneKeyRecyclerAdapter mAdapter;

    private PointViewFull pointView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // initToolBar();
        initRecyclerView();

//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnekeyResultLocal r = new OnekeyResultLocal("苯检测完成",
//                        BitmapFactory.decodeResource(getResources(),R.drawable.ic_login),true);
//                mAdapter.remove(0);
//                mAdapter.add(r, 0);
//                mAdapter.notifyItemChanged(0);
//                pointView.setCurrText("准备下一项中");
//                pointView.setCurrScore(79);
//            }
//        });
//
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnekeyResultLocal r = new OnekeyResultLocal("PM2.5检测完成",
//                        BitmapFactory.decodeResource(getResources(),R.drawable.ic_login),true);
//                mAdapter.remove(1);
//                mAdapter.add(r, 1);
//                mAdapter.notifyItemChanged(1);
//                pointView.setCurrText("检测完成");
//                pointView.setCurrScore(59);
//                pointView.waitingStop();
//            }
//        });
//
//        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnekeyResultLocal r = new OnekeyResultLocal("苯检测中",
//                        BitmapFactory.decodeResource(getResources(),R.drawable.ic_login),false);
//                mAdapter.add(r, OneKeyRecyclerAdapter.LAST_POSITION);
//
//                pointView.setCurrText("检测苯含量");
//
//            }
//        });
//        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnekeyResultLocal r = new OnekeyResultLocal("PM2.5检测中",
//                        BitmapFactory.decodeResource(getResources(),R.drawable.ic_login),false);
//                mAdapter.add(r, OneKeyRecyclerAdapter.LAST_POSITION);
//                pointView.setCurrText("检测PM25");
//            }
//        });
//
//        pointView = (PointViewFull) findViewById(R.id.one_key_pointView);
//        pointView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pointView.waitingStart();
//                pointView.setCurrText("连接检测设备");
//            }
//        });

        pointView.setCurrActivity(this);

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.one_key_recycler_view);
        recyclerView.setHasFixedSize(true);
        //设置布局管理器 添加移除动画 添加分隔符 设置适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlideInDownAnimator animator = new SlideInDownAnimator(new OvershootInterpolator(0f));
        recyclerView.setItemAnimator(animator);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        mAdapter = new OneKeyRecyclerAdapter(null);

        recyclerView.setAdapter(mAdapter);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.onekye_check;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("一键检测");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);
    }
}
