package com.olife.o_life.util;

import android.content.Context;
import android.content.SharedPreferences;


import com.olife.o_life.MyApplication;
import com.olife.o_life.entity.User;

/**
 * Created by fuhai on 2017/6/5.
 * 用户类的工具 保存已经登录的用户的信息在本地，并且可以取出来
 */

public class UserUtils {
    /**
     * 获取当前的用户 如果没有登录就返回空
     *
     * @return
     */
    private static String SPNAME = UserUtils.class.getName();
    private static int MODE = Context.MODE_PRIVATE;
    private static String ID = "userId";
    private static String URL = "url";
    private static String SEX = "sex";
    private static String AGE = "age";
    private static String BRITHDAY = "brithday";
    private static String NAME = "name";
    private static String EMAIL = "email";
    private static String PHONE = "phone";
    private static String PASS = "pass";
    private static String PHOVE = "phove";
    private static String EMAILVE = "emailve";


    public static User currentUser() {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(SPNAME, MODE);
        int id =sharedPreferences.getInt(ID, -1);
        String url = sharedPreferences.getString(URL, "");
        String sex = sharedPreferences.getString(SEX, "");
        String age = sharedPreferences.getString(AGE, "");
        String email = sharedPreferences.getString(EMAIL, "");
        String pass = sharedPreferences.getString(PASS, "");
        String phone = sharedPreferences.getString(PHONE, "");
        String emailve = sharedPreferences.getString(EMAILVE, "");
        String phoneve = sharedPreferences.getString(PHOVE, "");
        String brithday = sharedPreferences.getString(BRITHDAY, "");
        String name = sharedPreferences.getString(NAME, "");
        if (name.equals("") || id == -1) {
            return null;
        } else {
            User user = new User();
            user.setAge(age);
            user.setBrithday(brithday);
            user.setUsername(name);
            user.setPassword(pass);
            user.setSex(sex);
            user.setEmail(email);
            user.setImgUrl(url);
            user.setEmailVe(emailve);
            user.setPhoneVe(phoneve);
            user.setId(id);
            user.setPhone(phone);

            return user;
        }


    }

    /**
     * 登录过之后保存下 在登录成功后调用
     *
     * @param user
     */
    public static void saveCurrentUser(User user) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(SPNAME, MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID, user.getId());
        editor.putString(URL, user.getImgUrl());
        editor.putString(SEX, user.getSex());
        editor.putString(AGE, user.getAge());
        editor.putString(BRITHDAY, user.getBrithday());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PHONE, user.getPhone());
        editor.putString(PASS, user.getPassword());
        editor.putString(PHOVE, user.getPhoneVe());
        editor.putString(EMAILVE, user.getEmailVe());
        editor.putString(NAME, user.getUsername());

        editor.commit();

    }

    /**
     * 移除当前的用户 在推出登录之后使用
     */
    public static void removeCurrentUser() {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(SPNAME, MODE);
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().commit();
        }

    }


}
