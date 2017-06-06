package com.olife.o_life.entity;

import cn.bmob.v3.BmobUser;

/**
 * 用户类
 * Created by chenfuhai on 2016/10/13 0013.
 */

public class User {
    private int id;
    private String username;
    private String password;
    private String age;
    private String phone;

    private String email;
    private String phoneVe;
    private String emailVe;
    private String imgUrl;
    private String sex;//性别 t为男 f为女
    private String brithday;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneVe() {
        return phoneVe;
    }

    public void setPhoneVe(String phoneVe) {
        this.phoneVe = phoneVe;
    }

    public String getEmailVe() {
        return emailVe;
    }

    public void setEmailVe(String emailVe) {
        this.emailVe = emailVe;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }



}
