package com.olife.o_life.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.jude.rollviewpager.RollPagerView;

/**
 * Created by wuguofei on 2016/12/5.
 */

/**
 * 周边空气 具体信息 详细和评论页面
 * 对于listview与viewpager的事件冲突，最好还是选择在重写viewpager方法，
 * 如果重写listview可能对其子控件造成影响。
 *
 * 重写dispatchTouchEvent方法，
 */
public class MyViewPager extends RollPagerView {
    private float mDownX;
    private float mDownY;


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * android系统中的每个ViewGroup的子类都具有下面三个和TouchEvent处理密切相关的方法：
     *
     * 分发touch事件
     * @param ev
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent ev){
        //通知父view，我的单击事件我自行处理，不要阻碍我
        //getParent().requestDisallowInterceptTouchEvent(true);
        Log.e("where1",ev+"");
        //判断事件
        switch(ev.getAction()){
            //事件
            case MotionEvent.ACTION_DOWN:
                //获取大小
                mDownX = ev.getX();
                mDownY = ev.getY();
                //让viewpager放弃对父控件事件干扰
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(ev.getX()-mDownX)>Math.abs(ev.getY()-mDownY)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拦截touch事件
     * @param arg0
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        Log.e("where2",arg0+"");
        return super.onInterceptTouchEvent(arg0);
    }

    /**
     * 处理touch事件
     * @param arg0
     * @return
     */
    public boolean onTouchEvent(MotionEvent arg0) {
        Log.e("where3",arg0+"");

        return super.onTouchEvent(arg0);
    }

}
