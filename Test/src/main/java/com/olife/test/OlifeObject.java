package com.olife.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.olife.test.listener.OlifeSaveLinstener;


import java.lang.reflect.Field;

/**
 * Created by chenfuhai on 2017/5/31 0031.
 *
 * 利用反射机制 得到子类的全部属性和内容 以及类名
 * 把属性和内容通过JSON的方式形成JSON数组 直接使用Gson
 * 发送JSON数组得到相应的返回码
 *
 * 服务器端 取得JSON数组里面的信息 再存入数据库
 */

public class OlifeObject {

    protected void save(OlifeSaveLinstener<?> saveLinstener ){
        //不忽略空值得属性全部转化为JSON
        Gson gson = new GsonBuilder().serializeNulls().create();

        System.out.println(gson.toJson(this));
    }



//    private JsonArray fields2Json(Field[] fields) {
//        Gson gson = new Gson();
//        gson.toJson()
//        JsonArray ja = new JsonArray();
//        try {
//            for (Field field:fields
//                    ) {
//                field.setAccessible(true);
//                JsonObject json = new JsonObject();
//
//                json.addProperty(field.getName(),field.get(this));
//                ja.add(json);
//            }
//        }catch (IllegalAccessException e){
//            e.printStackTrace();
//        }
//
//        return ja;
//    }
}
