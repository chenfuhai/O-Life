package com.olife.o_life.biz;

import com.olife.o_life.entity.Goods;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by wuguofei on 2016/12/26.
 */

public interface GoodsBiz {
    interface FindGoodsListener{
        /**
         * 开始时做的事情
         */
        void onStart();
        /**
         * 成功时做的事情
         * @param list
         */
        void onSuccess(List<Goods> list);
        /**
         * 失败时做的事情
         * @param e
         */
        void onFailed(int e);
    }
    void FindAllGoods(int limit, int skit, FindGoodsListener listener);
}
