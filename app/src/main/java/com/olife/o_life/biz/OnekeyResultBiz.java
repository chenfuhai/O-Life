package com.olife.o_life.biz;

import com.olife.o_life.entity.OnekeyResultRecord;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 一键检测的结果记录 查询 增加 等
 * Created by chenfuhai on 2016/12/16 0016.
 */

public interface OnekeyResultBiz {
    interface OnekeyDoingLisenter {
        /**
         * 开始的的时候要做的事
         */
        void onStart();

        /**
         * 成功的时候要做的事
         *
         */
        void onSuccess();

        /**
         * 失败的时候要做的事情
         */
        void onFailed(int e);
    }
    interface FindDoingLisenter {
        /**
         * 开始的的时候要做的事
         */
        void onStart();

        /**
         * 成功的时候要做的事
         *
         */
        void onSuccess(List<OnekeyResultRecord> records);

        /**
         * 失败的时候要做的事情
         */
        void onFailed(int e);
    }

    interface FindLastDoingLisenter {
        /**
         * 开始的的时候要做的事
         */
        void onStart();

        /**
         * 成功的时候要做的事
         *
         */
        void onSuccess(OnekeyResultRecord record);

        /**
         * 失败的时候要做的事情
         */
        void onFailed(int e);
    }
    /**
     * 保存用户的检测记录
     * @param onekeyResultRecord
     * @param lisenter
     */
    void saveOnekeyResult(OnekeyResultRecord onekeyResultRecord, OnekeyDoingLisenter lisenter);

    /**
     * 查询用户的检测记录 count默认10条
     * @param userId
     * @param count
     * @param lisenter
     */
    void findOnkeyResultByUserId(String userId,int count,int skip,FindDoingLisenter lisenter);

    /**
     * 查询用户的最新的一条检测记录
     * @param userId
     * @param lisenter
     */
    void findLastOnkeyResultByUserId(String userId,FindLastDoingLisenter lisenter);

}
