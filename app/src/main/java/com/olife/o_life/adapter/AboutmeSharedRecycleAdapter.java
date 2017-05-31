package com.olife.o_life.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olife.o_life.R;
import com.olife.o_life.entity.OnekeyResultRecord;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chenfuhai on 2016/12/27 0027.
 */


public class AboutmeSharedRecycleAdapter extends RecyclerView.Adapter<AboutmeSharedRecycleAdapter.MainAdapterHolder> {


    class MainAdapterHolder extends RecyclerView.ViewHolder {

        public TextView tvMark;
        public TextView tvInfo;

        public MainAdapterHolder(View itemView) {
            super(itemView);
            tvMark = (TextView) itemView.findViewById(R.id.item_recycle_history_mark);
            tvInfo = (TextView) itemView.findViewById(R.id.item_recycle_history_info);
        }
    }


    private ArrayList<OnekeyResultRecord> arrayList;


    /**
     * 添加新的数据进来
     *
     * @param newList
     */
    public void addAll(List<OnekeyResultRecord> newList) {
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

    public void setDataList(ArrayList<OnekeyResultRecord> list) {
        this.arrayList = list;
    }

    public ArrayList<OnekeyResultRecord> getDataLit() {
        return this.arrayList;
    }


    public AboutmeSharedRecycleAdapter(ArrayList<OnekeyResultRecord> arrayList) {
        this.arrayList = arrayList;

    }

    @Override
    public AboutmeSharedRecycleAdapter.MainAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_shared, parent, false);
        return new AboutmeSharedRecycleAdapter.MainAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(AboutmeSharedRecycleAdapter.MainAdapterHolder holder, int position) {
        OnekeyResultRecord resultRecord = arrayList.get(position);

        holder.tvMark.setText(resultRecord.getResultMark().toString());
        holder.tvInfo.setText("苯："+resultRecord.getBen()+"\tPM2.5"+resultRecord.getPm2_5()+"\t地理位置"
                +resultRecord.getDistrict()+resultRecord.getStreet()+resultRecord.getStreetNum()
                +"\t时间:"+resultRecord.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
