package com.olife.o_life.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olife.o_life.R;

/**
 * 推荐商品 上里面的广告ViewPager 适配器
 * Created by wuguofei on 2016/12/3.
 */

public class ReAdAdapter extends LoopPagerAdapter {

    private int[] imgs = {
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p4,
            R.drawable.p3,
    };

    public ReAdAdapter(RollPagerView viewPager) {
        super(viewPager);
    }


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());

        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }
}
