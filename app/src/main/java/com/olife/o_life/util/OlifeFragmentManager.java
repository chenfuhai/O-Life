package com.olife.o_life.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static cn.bmob.newim.db.dao.MessageDao$Properties.Content;

/**
 * Created by chenfuhai on 2016/12/12 0012.
 * 进行首页的fragment管理 包括启动 加载  显示 隐藏 添加
 */

public class OlifeFragmentManager {

    private Activity mActivity;
    private int mFragmentPosition;
    private ArrayList<Fragment> mFragments;


    public OlifeFragmentManager(Activity currActivity, int fragmentPosition) {
        this.mActivity = currActivity;
        this.mFragmentPosition = fragmentPosition;
        mFragments = new ArrayList<>();
    }




    /**
     * 将需要添加的fragment全部添加进来
     *
     * @param fragments
     * @param fragmentTags
     * @param defultShowIndex 默认显示的fragment在fragments中的下标  超出范围默认为0
     */
    public void addFragments(ArrayList<Fragment> fragments, ArrayList<String> fragmentTags,  int defultShowIndex) {
        if (defultShowIndex < 0 || defultShowIndex > fragments.size() || defultShowIndex == fragments.size()) {
            defultShowIndex = 0;
        }
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            String fragmentTag = fragmentTags.get(i);
            if (i == defultShowIndex) {

                ft.add(mFragmentPosition, fragment, fragmentTag).show(fragment);
            } else {
                ft.add(mFragmentPosition, fragment, fragmentTag).hide(fragment);
            }
        }
        ft.commit();
        mFragments = fragments;
    }

    /**
     * @param
     */
    public void showFragment(String fragmentTag) {
        FragmentManager fm = mActivity.getFragmentManager();
        Fragment showFragment = fm.findFragmentByTag(fragmentTag);

        if (showFragment.isHidden()) {
            FragmentTransaction ft = fm.beginTransaction();
            //隐藏当前的 显示要显示的 如果当前的和要显示的都没有添加进来 那么就先添加 这个FragmentTransaction表示了一种状态
            //如果要其中的一个显示 那么其他的也都要设置一遍隐藏
            for (int i = 0; i < mFragments.size(); i++) {
                ft.hide(mFragments.get(i));
            }
            ft.show(showFragment);
            ft.commit();
        }

    }


}
