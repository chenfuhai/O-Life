package com.olife.o_life.biz;

import com.olife.o_life.entity.Feedback;
import com.olife.o_life.entity.OnekeyResultRecord;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 反馈意见处理Biz
 *
 * Created by chenfuhai on 2016/12/26 0026.
 */

public interface FeedBackBiz {
    interface FeedbackDoingLisenter {
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
        void onFailed(BmobException e);
    }

    interface FindFeedbacksLisenter {
        /**
         * 开始的的时候要做的事
         */
        void onStart();

        /**
         * 成功的时候要做的事
         *
         */
        void onSuccess(List<Feedback> feedbacks);

        /**
         * 失败的时候要做的事情
         */
        void onFailed(BmobException e);
    }


    /**
     * 提交用户反馈
     * @param feedback 反馈类
     * @param lisenter 结果监听
     */
    void submitFeedBack(Feedback feedback , FeedbackDoingLisenter lisenter);
    /**
     * 查找用户反馈  分页查询 比如 有200条数据 我要分页来显示 每一页15条
     * <br/> 第一页的时候查询一次limit 15 skip = 0
     ** <br/> 第二页的时候再查询一次limit 15 skip = 15*1
     ** <br/> 第三页的时候再查询一次limit 15 skip = 15*2
     *<br/>  以此类推
     *  @param limit 每一页所要展示的数目
     *  @param skip 跳过前面的多少条 一般等于页数*limit
     * @param lisenter 结果监听
     */
    void findAllFeedBack(int limit,int skip,FindFeedbacksLisenter lisenter);
}
