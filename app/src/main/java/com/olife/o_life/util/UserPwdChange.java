package com.olife.o_life.util;

/**
 * Created by fuhai on 2017/6/5.
 */

/**
 * id 用户的id
 * oldPwd旧密码
 * newPwd 新密码
 */
public class UserPwdChange {
    private int id;
    private String oldPwd;
    private String newPwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
