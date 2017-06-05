package com.olife.o_life.biz;

import com.olife.o_life.entity.OnekeySharedMessage;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 周边空气分享BIZ 分享的上传 下载 依据时间 距离当前位置多少 下载附近的分享 根据用户的ID下载用户自己的分享
 * Created by chenfuhai on 2016/12/16 0016.
 */

public interface OnekeySharedMessageBiz {
    interface DiscoverDoingLisenter {
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
    interface FindSharedDoingLisenter {
        /**
         * 开始的的时候要做的事
         */
        void onStart();

        /**
         * 成功的时候要做的事
         *
         */
        void onSuccess(List<OnekeySharedMessage> objects);

        /**
         * 失败的时候要做的事情
         */
        void onFailed(int e);
    }

    /**
     * 分享自己的数据
     * @param sharedMessage
     * @param lisenter
     */
    void shareUserResult(OnekeySharedMessage sharedMessage, DiscoverDoingLisenter lisenter );

    /**
     * 以传入的点的经纬度为中心 找到距离该点 距离为 x 的点的集合 在其中定义距离
     * @param lat 纬度
     * @param lng 经度
     * @param lisenter 结果回调监听
     */
    //以后再把时间限制掉
    void findOthersSharedByLatLng(double lat,double lng,FindSharedDoingLisenter lisenter);



    /**
     * 查询用户的分享记录 count默认10条
     * @param userId
     * @param count
     * @param lisenter
     */
    void findOnkeySharedByUserId(String userId,int count,FindSharedDoingLisenter lisenter);

}
