package com.olife.o_life.entity;

import cn.bmob.v3.BmobUser;

/**
 * 用户类
 * Created by chenfuhai on 2016/10/13 0013.
 */

public class User extends BmobUser {
    private String imgUrl;
    private Boolean sex;//性别 t为男 f为女
    private String brithday;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 真 为男 false 为女
     * @return
     */
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }
}
