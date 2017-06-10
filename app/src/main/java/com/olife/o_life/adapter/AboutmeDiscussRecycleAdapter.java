package com.olife.o_life.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olife.o_life.R;
import com.olife.o_life.entity.OnekeyResultRecord;
import com.olife.o_life.entity.OnekeySharedDisc;
import com.olife.o_life.util.NetConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenfuhai on 2016/12/27 0027.
 */


public class AboutmeDiscussRecycleAdapter extends RecyclerView.Adapter<AboutmeDiscussRecycleAdapter.MainAdapterHolder> {


    class MainAdapterHolder extends RecyclerView.ViewHolder {

        public ImageView ivHead;
        public TextView tvName;
        public TextView tvTime;
        public ImageView ivSex;
        public TextView tvContent;

        public MainAdapterHolder(View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.item_recycle_discuss_img_iv);
            tvName = (TextView) itemView.findViewById(R.id.item_recycle_discuss_name_tv);
            tvTime = (TextView) itemView.findViewById(R.id.item_recycle_discuss_time_tv);
            ivSex = (ImageView) itemView.findViewById(R.id.item_recycle_discuss_sex_iv);
            tvContent = (TextView) itemView.findViewById(R.id.item_recycle_discuss_content_tv);

        }
    }


    private ArrayList<OnekeySharedDisc> arrayList;


    /**
     * 添加新的数据进来
     *
     * @param newList
     */
    public void addAll(List<OnekeySharedDisc> newList) {
        int lastIndex = this.arrayList.size();
        if (this.arrayList.addAll(newList)) {
            notifyItemRangeInserted(lastIndex, arrayList.size());
        }

    }

    /**
     * 移除某一个
     *
     * @param position
     */
    public void remove(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清除整个list
     */
    public void clear() {
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public void setDataList(ArrayList<OnekeySharedDisc> list) {
        this.arrayList = list;
    }

    public ArrayList<OnekeySharedDisc> getDataLit() {
        return this.arrayList;
    }


    public AboutmeDiscussRecycleAdapter(ArrayList<OnekeySharedDisc> arrayList) {
        this.arrayList = arrayList;

    }

    @Override
    public AboutmeDiscussRecycleAdapter.MainAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_discuss, parent, false);
        return new AboutmeDiscussRecycleAdapter.MainAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(AboutmeDiscussRecycleAdapter.MainAdapterHolder holder, int position) {
        OnekeySharedDisc sharedDisc = arrayList.get(position);

        holder.tvContent.setText(sharedDisc.getMessage());
        holder.tvName.setText(sharedDisc.getUsername());
        holder.tvTime.setText(sharedDisc.getTime());
        boolean sex = sharedDisc.getUsersex().booleanValue();
        if (sex) {
            holder.ivSex.setImageResource(R.drawable.sex_male);
        } else {
            holder.ivSex.setImageResource(R.drawable.sex_woman);
        }
        String userimgUrl = sharedDisc.getUserImgUrl();
        if (userimgUrl != null) {
            //显示图片的配置
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.test_icon)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(NetConfig.PreUrl+userimgUrl, holder.ivHead, options);
        } else {
            holder.ivHead.setImageResource(R.drawable.test_icon);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
