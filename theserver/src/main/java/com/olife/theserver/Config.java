package com.olife.theserver;

/**
 * Created by chenfuhai on 2017/4/11 0011.
 */

public class Config {
    public static String SERVER_IP="127.0.0.1";
    public static String SERVER_POINT ="8080";
    public static String SERVER_NAME = "TheProject";
    public static  String getServerUrl(){
        return "http://"+SERVER_IP+":"+SERVER_POINT+"/"+SERVER_NAME+"/";
    }


}
