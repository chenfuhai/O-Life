package com.olife.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import java.util.ArrayList;

public class RecycleViewActivity extends AppCompatActivity {

    private LRecyclerView mRecyclerView;
    private ArrayList<String> arrayList;
    private MainAdapter adapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    private int count = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1://正常情况
                    for (int i = 0; i < 5; i++) {
                        arrayList.add("the new " + i);
                    }
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                    adapter.notifyDataSetChanged();
                    break;
                case -2://网络出错
                    adapter.notifyDataSetChanged();
                    RecyclerViewStateUtils.setFooterViewState(RecycleViewActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestData();
                        }
                    });
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);


        /*找到控件*/
        mRecyclerView = (LRecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        for (int i = 0; i < REQUEST_COUNT; i++) {
            arrayList.add("d" + i);
        }

        adapter = new MainAdapter(arrayList);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
                        if (state == LoadingFooter.State.Loading) {
                            Toast.makeText(RecycleViewActivity.this, "加载中，请稍后", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (count <= 50) {
                            // loading more
                            RecyclerViewStateUtils.setFooterViewState(RecycleViewActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                            requestData();
                        } else {
                            //the end
                            RecyclerViewStateUtils.setFooterViewState(RecycleViewActivity.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);

                        }
                    }
                });
            }
        });


    }


    /**
     * 模拟请求网络
     */
    private void requestData() {
        count = +adapter.getItemCount();
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (NetworkUtils.isNetAvailable(RecycleViewActivity.this)) {
                    handler.sendEmptyMessage(-1);
                } else {
                    handler.sendEmptyMessage(-2);
                }

            }
        }.start();
    }


}

class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterHolder> {


    class MainAdapterHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView tv;

        public MainAdapterHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_onekey_icon);
            tv = (TextView) itemView.findViewById(R.id.item_onekey_message);
        }
    }


    private ArrayList<String> arrayList;

    public MainAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MainAdapter.MainAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onekey_recycler, parent, false);
        return new MainAdapter.MainAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainAdapterHolder holder, int position) {
        String s = arrayList.get(position);
        holder.tv.setText(s);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}



