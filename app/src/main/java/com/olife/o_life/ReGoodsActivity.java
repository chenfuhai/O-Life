//package com.olife.o_life;
//
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListAdapter;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.Toast;
//
//import com.jude.rollviewpager.RollPagerView;
//import com.olife.o_life.adapter.ReGoodsLVAdapter;
//import com.olife.o_life.adapter.ReAdAdapter;
//import com.olife.o_life.view.ReGoddsListView;
//import com.olife.o_life.view.PullToRefreshView;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 推荐商品的activity版本
// * Created by wuguofei on 2016/12/6.
// */
//
//public class ReGoodsActivity extends ActionBarActivity implements PullToRefreshView.OnHeaderRefreshListener,
//        PullToRefreshView.OnFooterRefreshListener {
//    private PullToRefreshView mPullToRefreshView;
//    private ScrollView scrollview;
//    private RollPagerView vp;
//    private ReGoddsListView listview;
//    List<Map<String, Object>> listMap;
//    private ReGoodsLVAdapter adapter;
//    private RelativeLayout rl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.regoods);
//        scrollview = (ScrollView)findViewById(R.id.scrollView_MainActivity);
//        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
//        vp = (RollPagerView)findViewById(R.id.viewPager_MainActivity);
//        listview = (ReGoddsListView)findViewById(R.id.listView_MainActivity);
//
//
//        ReAdAdapter loopPagerAdapter = new ReAdAdapter(vp);
//        vp.setAdapter(loopPagerAdapter);
//        initListView();
//
//        mPullToRefreshView.setOnHeaderRefreshListener(ReGoodsActivity.this);
//        mPullToRefreshView.setOnFooterRefreshListener(this);
//        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
//
//    }
//    private void initListView() {
//        listview = (ReGoddsListView) findViewById(R.id.listView_MainActivity);
//        listMap = new ArrayList<>();
//        for(int i = 0; i < 10; i++){
//            Map<String, Object> map = new HashMap<>();
//            map.put("title", "植物" + i);
//            map.put("content", "介绍：" + i);
//            listMap.add(map);
//        }
//        adapter = new ReGoodsLVAdapter(this,R.layout.regoods_lisetview_top,listMap);
//        listview.setAdapter(adapter);
//        //动态算出ListView的LayoutParams，并设置到ListView中
//        ViewGroup.LayoutParams params = getListViewParams();
//        listview.setLayoutParams(params);
//
//        scrollview = (ScrollView) findViewById(R.id.scrollView_MainActivity);
//        scrollview.smoothScrollTo(0, 0);
//    }
//    /**
//     * 动态的算出ListView实际的LayoutParams
//     * 最关键的是算出LayoutParams.height
//     */
//    public ViewGroup.LayoutParams getListViewParams() {
//        //通过ListView获取其中的适配器adapter
//        ListAdapter listAdapter = listview.getAdapter();
//
//        //声明默认高度为0
//        int totalHeight = 0;
//        //便利ListView所有的item，累加所有item的高度就是ListView的实际高度
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listview);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        //将累加获取的totalHeight赋值给LayoutParams的height属性
//        ViewGroup.LayoutParams params = listview.getLayoutParams();
//        params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
//        return params;
//    }
//
//    @Override
//    public void onFooterRefresh(PullToRefreshView view) {
//        mPullToRefreshView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPullToRefreshView.onFooterRefreshComplete();
//                loadmore();
//                Toast.makeText(ReGoodsActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
//            }
//
//        }, 1000);
//    }
//    public void loadmore(){
//        for(int i = 10; i < 15; i++){
//            Map<String, Object> map = new HashMap<>();
//            map.put("title", "植物" + i);
//            map.put("content", "介绍：" + i);
//            listMap.add(map);
//        }
//        adapter = new ReGoodsLVAdapter(this, R.layout.regoods_lisetview_top, listMap);
//        listview.setAdapter(adapter);
//    }
//
//    @Override
//    public void onHeaderRefresh(PullToRefreshView view) {
//
//    }
//}