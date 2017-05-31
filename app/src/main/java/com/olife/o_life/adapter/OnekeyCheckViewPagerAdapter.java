package com.olife.o_life.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenfuhai on 2016/12/15 0015.
 */

public class OnekeyCheckViewPagerAdapter extends PagerAdapter {

    /***
     * ViewPager适配器
     *
     * @author chenfuhai
     */
    private List<View> list;

    public OnekeyCheckViewPagerAdapter(List<View> datalist) {
        this.list = datalist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}