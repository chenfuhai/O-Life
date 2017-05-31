package com.olife.o_life.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * 数据缓存管理
 * <p>
 * <br/>SD卡上的<br/>
 * <br/>Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
 * <br/>Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
 * <p>
 * <br/>内部的
 * <br/>Context.getFilesDir()
 * <br/>Context.getCacheDir()
 * <p>
 * <br/>File可以保存很久 chche会被清除 当存储不够的时候
 * <p>
 * <br/>Created by chenfuhai on 2016/12/26 0026.
 */

public class DataCleanManager {

    /**
     * 获得缓存大小
     *
     * @param context
     * @return
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 获得文件数据的大小
     *
     * @param context
     * @return
     */
    public static String getTotalFilesSize(Context context) {
        long filesize = getFolderSize(context.getFilesDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC));//音乐
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS));//音频
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES));//铃声
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_ALARMS));//闹铃
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));//通知铃声
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));//图片
            filesize += getFolderSize(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));//视频
        }
        return getFormatSize(filesize);
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {

        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 清除文件数据
     *
     * @param context
     */
    public static void clearAllFiles(Context context) {
        deleteDir(context.getFilesDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC));//音乐
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS));//音频
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES));//铃声
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_ALARMS));//闹铃
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));//通知铃声
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));//图片
            deleteDir(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));//视频
        }
    }


    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件大长度

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            // 如果下面还有文件
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}