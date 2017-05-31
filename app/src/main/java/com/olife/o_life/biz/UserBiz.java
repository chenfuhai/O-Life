package com.olife.o_life.biz;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.olife.o_life.entity.User;

import cn.bmob.v3.exception.BmobException;

/**
 *
 * Created by chenfuhai on 2016/12/9 0009.
 */

public interface UserBiz {

    interface UserDoingLisenter {
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

    /**
     * 更新用户头像
     * @param user 用户类
     * @param imgmap 用户的头像map
     * @param lisenter 结果回调接口
     */
    void updateUserHead( User user, Bitmap imgmap,@NonNull UserDoingLisenter lisenter);

    /**
     * 更新用户信息
     * @param user 用户类 使用新建一个用户 然后设置其需要更新的内容 记得加入用户的ID
     * @param lisenter 结果回调接口
     */
    void updateUser(User user,@NonNull UserDoingLisenter lisenter);


    /**
     *
     * 修改当前用户的密码

     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param lisenter 结果回调接口
     */
    void changePwd(String oldPwd,String newPwd,@NonNull UserDoingLisenter lisenter);

    /**
     * 验证手机验证码
     * @param phone 手机号
     * @param code 验证码
     * @param lisenter 结果回调接口
     */

    void verifySmsCode(String phone,String code,@NonNull UserDoingLisenter lisenter);

    /**
     * 请求手机验证码
     * @param phone 手机号码
     * @param lisenter 结果回调接口
     */
    void requestSMSCode(String phone,@NonNull UserDoingLisenter lisenter);

    /**
     * 验证码登录
     * @param phone 手机号
     * @param code 验证码
     * @param lisenter 结果回调接口
     */
    void loginBySMSCode(String phone,String code,@NonNull UserDoingLisenter lisenter);
}
