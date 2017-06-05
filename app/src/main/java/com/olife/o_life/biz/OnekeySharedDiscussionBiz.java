package com.olife.o_life.biz;

import com.olife.o_life.entity.OnekeySharedDisc;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 周边空气用户评论 根据位置下载用户评论 增加用户评论到某一个分享 根据用户的ID查询用户所有的评论
 * Created by chenfuhai on 2016/12/16 0016.
 */

public interface OnekeySharedDiscussionBiz {

    interface DiscussionDoingLisenter {
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
    interface FindDiscussionsLisenter {
        /**
         * 开始的的时候要做的事
         */
        void onStart();

        /**
         * 成功的时候要做的事
         *
         */
        void onSuccess(List<OnekeySharedDisc> discussions);

        /**
         * 失败的时候要做的事情
         */
        void onFailed(int e);
    }
    /**
     * 发表评论
     * @param discussion 要发表的评论
     * @param lisenter 发表结果监听类
     */
    void reportDiscussion(OnekeySharedDisc discussion, DiscussionDoingLisenter lisenter);

    /**
     * 根据分享的信息来查找对应的评论 暂时查找所有的评论 分页查询 比如 有200条数据 我要分页来显示 每一页15条
     * <br/> 第一页的时候查询一次limit 15 skip = 0
     ** <br/> 第二页的时候再查询一次limit 15 skip = 15*1
     ** <br/> 第三页的时候再查询一次limit 15 skip = 15*2
     *<br/>  以此类推
     *  @param limit 每一页所要展示的数目
     *  @param skip 跳过前面的多少条 一般等于页数*limit
     * @param messageId 分享的ID
     * @param lisenter 结果监听
     */
    void findMessageAllDiscussions(String messageId, int limit, int skip, FindDiscussionsLisenter lisenter);

    /**
     * 查找用户所有发表的评论  分页查询 比如 有200条数据 我要分页来显示 每一页15条
     * <br/> 第一页的时候查询一次limit 15 skip = 0
     ** <br/> 第二页的时候再查询一次limit 15 skip = 15*1
     ** <br/> 第三页的时候再查询一次limit 15 skip = 15*2
     *<br/>  以此类推
     *  @param limit 每一页所要展示的数目
     *  @param skip 跳过前面的多少条 一般等于页数*limit
     * @param userId 用户的ID
     * @param lisenter 结果监听
     */
    void findUserAllDiscussions(String userId,int limit,int skip,FindDiscussionsLisenter lisenter);

    /**
     * 删除评论

     * @param discussionId 评论的Id
     * @param lisenter 结果监听
     */
    void deleteDiscussion(String discussionId, DiscussionDoingLisenter lisenter);

    /**
     * 删除用户所有的评论
     * @param userId 用户的Id
     * @param lisenter 结果监听
     */
    void deleteUserAllDiscussion(String userId, DiscussionDoingLisenter lisenter);

}
