package com.olife.o_life.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 获取单前时间 格式化处理
 * Created by chenfuhai on 2017/6/6 0006.
 */

public class TimeUtils {
    public static String currentTimeFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        Date date= new Date();
        return sdf.format(date);
    }

}
