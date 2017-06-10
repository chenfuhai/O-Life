package com.olife.o_life;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.olife.o_life.adapter.AboutmeDiscussRecycleAdapter;
import com.olife.o_life.biz.OnekeySharedDiscussionBiz;
import com.olife.o_life.bizImpl.OnekeySharedDiscussionBizImpl;
import com.olife.o_life.entity.OnekeySharedDisc;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.NetworkUtils;
import com.olife.o_life.util.UserUtils;

import java.util.ArrayList;
import java.util.List;

public class AboutmeDiscussActivity extends ToolBarBaseActivity {

    /**
     * 每一页展示多少条数据
     */
    private final int REQUEST_COUNT = 5;
    /**
     * 已经获取到多少条数据了 静态的变量不能随便乱设置 类存在就存在了
     */
    private int mCurrentCounter = 0;


    private LRecyclerView mRecyclerView;
    private ArrayList<OnekeySharedDisc> arrayList;
    private AboutmeDiscussRecycleAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    private OnekeySharedDiscussionBizImpl discussionBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();

        discussionBiz = new OnekeySharedDiscussionBizImpl();

        arrayList = new ArrayList<>();
        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        adapter = new AboutmeDiscussRecycleAdapter(arrayList);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
                if (state == LoadingFooter.State.Loading) {
                    Toast.makeText(AboutmeDiscussActivity.this, "加载中，请稍后", Toast.LENGTH_SHORT).show();
                    return;
                }
                //上拉的时候就设置为加载中 然后请求网络的数据 请求成功以后进行相应的操作
                RecyclerViewStateUtils.setFooterViewState(AboutmeDiscussActivity.this, mRecyclerView,
                        REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            }
        });

        requestData();
    }

    /**
     * 请求网络数据
     */
    private void requestData() {
        if (NetworkUtils.isNetAvailable(getApplicationContext())) {
            //网络可用
            //User user = UserUtils.currentUser();
            User user = UserUtils.currentUser();
            if (user != null) {
                discussionBiz.findUserAllDiscussions(user.getId()+"", REQUEST_COUNT, mCurrentCounter, new OnekeySharedDiscussionBiz.FindDiscussionsLisenter() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<OnekeySharedDisc> discussions) {
                        //如果没有数据 说明到底了 就加载一个到底的说明 如果有 就直接加载

                        if (discussions == null || discussions.size() == 0) {
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.addAll(discussions);
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                            adapter.notifyDataSetChanged();
                            mCurrentCounter = adapter.getItemCount();
                        }
                    }

                    @Override
                    public void onFailed(int e) {
                        //BmobError.showErrorMessage(getApplicationContext(), e);
                        RecyclerViewStateUtils.setFooterViewState(AboutmeDiscussActivity.this, mRecyclerView, REQUEST_COUNT,
                                LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        requestData();
                                    }
                                });
                    }
                });
            }
        } else {
            //网络不可用 设置为网络错误
            adapter.notifyDataSetChanged();
            RecyclerViewStateUtils.setFooterViewState(AboutmeDiscussActivity.this, mRecyclerView, REQUEST_COUNT,
                    LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestData();
                        }
                    });
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutme_history;
    }

    private void initToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的评论");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);

        final Drawable upArrow = ContextCompat.getDrawable(getApplicationContext(),
                android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);

        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationIcon(upArrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
