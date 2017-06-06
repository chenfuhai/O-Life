package com.olife.o_life.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olife.o_life.R;
import com.olife.o_life.entity.OnekeySharedDisc;
import com.olife.o_life.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * 周边空气的评论adapter
 * Created by wuguofei on 2016/12/2.
 */

public class DiscoveryDiscussAdapter extends RecyclerView.Adapter<DiscoveryDiscussAdapter.ViewHolder> implements View.OnClickListener{
    List<OnekeySharedDisc> datas;
    private Context context;
    private OnRecycleViewtemClickListener mOnItemClickListener = null;
    //定义接口
    public static interface OnRecycleViewtemClickListener {
        void onItemClick(View view, String datas);
    }

    public DiscoveryDiscussAdapter(List<OnekeySharedDisc> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public List<OnekeySharedDisc> getDataList() {
        return datas;
    }

    public void removeAllDataList() {
        this.datas.removeAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discovery_discuss, null);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecycleViewtemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    //绑定数据
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(datas.get(position).getUsername());
        holder.content.setText(datas.get(position).getMessage());
        holder.time.setText(datas.get(position).getTime());
        new Thread(new Runnable() {
            @Override
            public void run() {
                //根据表中图片的url地址来得到图片（Bitmap类型）
                final Bitmap bitmap = getPicture(BmobUser.getCurrentUser(User.class).getImgUrl());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                holder.headphoto.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.headphoto.setImageBitmap(bitmap);//将图片放到视图中去
                    }
                });
            }
        }).start();
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;
        public TextView content;
        public ImageView headphoto;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
            headphoto = (ImageView)itemView.findViewById(R.id.base_swipe_item_icon);
        }
    }
    //根据图片的url地址得到图片
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //bm = BitmapZoom.resizeImage(bm , 300 , 300);//修改图片大小
        return  bm;
    }


}

