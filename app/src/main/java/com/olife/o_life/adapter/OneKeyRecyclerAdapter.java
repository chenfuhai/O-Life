package com.olife.o_life.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.olife.o_life.R;
import com.olife.o_life.entity.OnekeyResultLocal;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenfuhai on 2016/12/6 0006.
 */

public class OneKeyRecyclerAdapter extends RecyclerView.Adapter<OneKeyRecyclerAdapter.OneKeyHolder> {
    private ArrayList<OnekeyResultLocal> mDataList;

    public static final int LAST_POSITION = -1;

    public OneKeyRecyclerAdapter(ArrayList<OnekeyResultLocal> dataList) {

        if (dataList != null) {
            mDataList = dataList;
        } else {
            mDataList = new ArrayList<>();
        }
    }

    public ArrayList<OnekeyResultLocal> getDataList() {
        return mDataList;
    }

    public void add(OnekeyResultLocal r, int position) {
        position = position == LAST_POSITION ? getItemCount() : position;
        mDataList.add(r);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position == LAST_POSITION && getItemCount() > 0)
            position = getItemCount() - 1;

        if (position > LAST_POSITION && position < getItemCount()) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public OneKeyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onekey_recycler, parent, false);
        return new OneKeyHolder(v);
    }

    @Override
    public void onBindViewHolder(final OneKeyHolder holder, int position) {
        OnekeyResultLocal r = mDataList.get(position);

        if (r.isCheckIsOk()) {
            holder.waitingProgress.setVisibility(View.GONE);
            holder.iv_checkOk.setVisibility(View.VISIBLE);
        } else {
            holder.iv_checkOk.setVisibility(View.GONE);
            holder.waitingProgress.setVisibility(View.VISIBLE);
        }

        holder.iv_head.setImageBitmap(r.getTagIcon());
        holder.tv_message.setText(r.getTipString());


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class OneKeyHolder extends RecyclerView.ViewHolder {
        public CircleImageView iv_head;
        public TextView tv_message;
        public ProgressBar waitingProgress;
        public ImageView iv_checkOk;

        public OneKeyHolder(View itemView) {
            super(itemView);
            iv_head = (CircleImageView) itemView.findViewById(R.id.item_onekey_icon);
            tv_message = (TextView) itemView.findViewById(R.id.item_onekey_message);
            waitingProgress = (ProgressBar) itemView.findViewById(R.id.item_onekey_waiting);
            iv_checkOk = (ImageView) itemView.findViewById(R.id.item_onekey_ok);
        }


    }
}
