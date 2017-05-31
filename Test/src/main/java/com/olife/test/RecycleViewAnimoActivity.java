package com.olife.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecycleViewAnimoActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<String> mDataList;

    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mDataList = new ArrayList<>();
        //initData();
        adapter = new HomeAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        recyclerView.setAdapter(adapter);//
        //设置Item增加、移除动画
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        SlideInUpAnimator slideInUpAnimator = new SlideInUpAnimator(new OvershootInterpolator(0f));

        recyclerView.setItemAnimator(slideInUpAnimator);



        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add("new String", HomeAdapter.LastLocation);
                recyclerView.scrollToPosition(mDataList.size()-1);
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(HomeAdapter.LastLocation);
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // initData();
                mDataList.remove(0);
                mDataList.add(0,"新的字符串");
                adapter.notifyItemChanged(0);
            }
        });


    }

    private void initData() {
       mDataList.clear();
        for (int i = 0; i < 10; i++) {
            mDataList.add("this is the " + i);
        }
    }


    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        public static final int LastLocation = -1;

        public void add(String s, int position) {
            position = position == LastLocation ? getItemCount() : position;
            mDataList.add(s);
            notifyItemInserted(position);
        }

        public void remove(int position) {
            if (position == LastLocation && getItemCount() > 0)
                position = getItemCount() - 1;

            if (position > LastLocation && position < getItemCount()) {
                mDataList.remove(position);
                notifyItemRemoved(position);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(mDataList.get(position)+position);
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecycleViewAnimoActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                }
            });
        }



        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.recyclerView_item);

            }
        }
    }
}
