package com.olife.o_life;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.olife.o_life.adapter.ReAdAdapter;
import com.olife.o_life.adapter.ReGoodsLVAdapter;
import com.olife.o_life.biz.GoodsBiz;
import com.olife.o_life.bizImpl.GoodsBizImpl;
import com.olife.o_life.entity.Goods;
import com.olife.o_life.util.StatusBarUtils;
import com.olife.o_life.view.PullToRefreshView;
import com.olife.o_life.view.ReGoddsListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 推荐商品的fragment版本
 * Created by wuguofei on 2016/12/6.
 */

public class ReGoodsFragment extends Fragment implements PullToRefreshView.OnHeaderRefreshListener,
        PullToRefreshView.OnFooterRefreshListener {
    List<Goods> list1 = new ArrayList<>();
    private PullToRefreshView mPullToRefreshView;
    private ScrollView scrollview;
    private RollPagerView vp;
    private ReGoddsListView listview;
    private ReGoodsLVAdapter adapter;
    private RelativeLayout rl;
    private View mView;
    //全局变量
    public int limit = 0, skit = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.regoods, null);
        scrollview = (ScrollView) mView.findViewById(R.id.scrollView_MainActivity);
        mPullToRefreshView = (PullToRefreshView) mView.findViewById(R.id.main_pull_refresh_view);
        vp = (RollPagerView) mView.findViewById(R.id.viewPager_MainActivity);
        listview = (ReGoddsListView) mView.findViewById(R.id.listView_MainActivity);


        ReAdAdapter loopPagerAdapter = new ReAdAdapter(vp);
        vp.setAdapter(loopPagerAdapter);
        initListView();

        mPullToRefreshView.setOnHeaderRefreshListener(ReGoodsFragment.this);
        mPullToRefreshView.setOnFooterRefreshListener(ReGoodsFragment.this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());

        initToolBar();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(getActivity(),
                ContextCompat.getColor(MyApplication.getContext(), R.color.bg_blue_deep), false);
    }

    /**
     * 初始化操作
     */
    private void initListView() {
        listview = (ReGoddsListView) mView.findViewById(R.id.listView_MainActivity);
        new GoodsBizImpl().FindAllGoods(limit + 3, 0, new GoodsBiz.FindGoodsListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<Goods> list) {
                list1 = list;
                //Log.e("1",list1+"");
                adapter = new ReGoodsLVAdapter(getActivity(), R.layout.regoods_lisetview_top, list);
                listview.setAdapter(adapter);
                //动态算出ListView的LayoutParams，并设置到ListView中
                ViewGroup.LayoutParams params = getListViewParams();
                listview.setLayoutParams(params);
                scrollview = (ScrollView) mView.findViewById(R.id.scrollView_MainActivity);
                scrollview.smoothScrollTo(0, 0);
            }

            @Override
            public void onFailed(int e) {
            }
        });
    }

    /**
     * 动态的算出ListView实际的LayoutParams
     * 最关键的是算出LayoutParams.height
     */
    public ViewGroup.LayoutParams getListViewParams() {
        //通过ListView获取其中的适配器adapter
        ListAdapter listAdapter = listview.getAdapter();

        //声明默认高度为0
        int totalHeight = 0;
        //便利ListView所有的item，累加所有item的高度就是ListView的实际高度
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //将累加获取的totalHeight赋值给LayoutParams的height属性
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
        return params;
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshView.onFooterRefreshComplete();
                loadmore();
                Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
            }

        }, 1000);
    }

    public void loadmore() {
        new GoodsBizImpl().FindAllGoods(1,skit+3, new GoodsBiz.FindGoodsListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<Goods> list) {
                //添加
                list1.addAll(list);
//                Log.e("2",list1+"");
//                Log.e("3",list+"");
                skit++;
                adapter = new ReGoodsLVAdapter(getActivity(), R.layout.regoods_lisetview_top, list1);
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailed(int e) {
            }
        });
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.setTitle("推荐商品");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);

    }


}