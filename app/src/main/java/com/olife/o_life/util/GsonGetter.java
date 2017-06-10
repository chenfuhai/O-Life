package com.olife.o_life.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.syncable;

/**
 * Created by chenfuhai on 2017/6/5 0005.
 */

public class GsonGetter {
   private Gson gson;
    private static  GsonGetter gsonGetter;
    private GsonGetter(){
        this.gson = new Gson();
    }
    public static  GsonGetter getInstance(){
        if (gsonGetter == null){
            synchronized (GsonGetter.class){
                if (gsonGetter == null){
                    gsonGetter = new GsonGetter();
                }
            }
        }
        return gsonGetter;
    }

    public Gson getGson(){
        return this.gson;
    }


}
