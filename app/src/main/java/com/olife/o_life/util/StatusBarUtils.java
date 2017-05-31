package com.olife.o_life.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by chenfuhai on 2016/12/11 0011.
 *
 * 状态栏、导航栏颜色修改变化 提供修改的快捷方式
 */

public class StatusBarUtils {

    /**
     * 设置状态栏的颜色变化
     * @param activity 当前的activity
     * @param color 颜色的值
     * @param isSetNavigationBar 是否设置底下的导航栏也是同样的颜色
     */
    public static void setWindowStatusBarColor(Activity activity, int color,boolean isSetNavigationBar) {
        try {
            //系统在5.0以上那么就可以修改
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor( color);

                if (isSetNavigationBar){
                    window.setNavigationBarColor(color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
