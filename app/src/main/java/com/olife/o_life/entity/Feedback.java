package com.olife.o_life.entity;

import cn.bmob.v3.BmobObject;

/**
 * 反馈类 用户反馈的信息存在这里面
 * Created by chenfuhai on 2016/12/26 0026.
 */

public class Feedback  {

    private int id;
    //用户的信息
    private String userId;
    private String userName;
    private String userAge;
    private String userSex;
    //反馈的内容
    private String message;
    //联系方式
    private String QQ;
    private String email;
    private String phone;

    public Feedback(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
