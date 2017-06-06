package com.olife.o_life.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.olife.o_life.R;
import com.olife.o_life.entity.Goods;
import com.olife.o_life.util.NetConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static android.os.Build.VERSION_CODES.N;
import static com.olife.o_life.R.id.tv1_re_goods;


/**
 * 推荐物品 物品列表的适配器
 * Created by wuguofei on 2016/12/3.
 */

public class ReGoodsLVAdapter extends BaseAdapter {
    //List<Map<String, Object>> datas;
    List<Goods> datas;
    private Context context;

    public ReGoodsLVAdapter(Context context, int re_goods_item1_fontdesign1, List<Goods> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    /**
     * 返回有样式的个数
     */
    public int getViewTypeCount() {
        return 1;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder1 viewHolder1;
        if (view == null) {
            viewHolder1 = new ViewHolder1();
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_regoods, null);
            viewHolder1.tv1_re_goods = (TextView) view.findViewById(tv1_re_goods);
            viewHolder1.tv2_re_goods = (TextView) view.findViewById(R.id.tv2_re_goods);
            viewHolder1.img_re_goods = (ImageView) view.findViewById(R.id.img_re_goods);
            view.setTag(viewHolder1);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //根据表中图片的url地址来得到图片（Bitmap类型）
                    final Bitmap bitmap = getPicture(NetConfig.PreImgUrl+datas.get(i).getIconUrl());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    viewHolder1.img_re_goods.post(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder1.img_re_goods.setImageBitmap(bitmap);//将图片放到视图中去
                        }
                    });
                }
            }).start();

        } else {
            viewHolder1 = (ViewHolder1) view.getTag();

        }
        viewHolder1.tv1_re_goods.setText(datas.get(i).getName());
        viewHolder1.tv2_re_goods.setText(datas.get(i).getDesc());
        viewHolder1.tv1_re_goods.setTextColor(Color.BLACK);


        return view;
    }

    class ViewHolder1 {
        private TextView tv1_re_goods;
        private TextView tv2_re_goods;
        private ImageView img_re_goods;
    }

    //根据图片的url地址得到图片
    public Bitmap getPicture(String path) {
        Bitmap bm = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bm = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}
