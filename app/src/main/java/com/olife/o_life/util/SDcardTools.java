package com.olife.o_life.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/***
 * 将用户的头像保存在sdcard 要先初始化
 */
public class SDcardTools {

    private static Context mContext;
    private static String PATH;

    public static void initialize(Context context) {
        mContext = context;
        PATH = mContext.getFilesDir() + File.separator + "O-life" + File.separator + "img";
    }

    public static String getPATH() {
        return PATH;
    }

    /**
     * 保存用户头像图片
     *
     * @param bitmap
     * @param username
     */
    public static void savaUserHead(Bitmap bitmap, String username) {
        username = username + ".png";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, outputStream);

        byte[] bytes = outputStream.toByteArray();
        saveFile(bytes, "user" + username);
        try {
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取用户头像图片
     *
     * @param username
     * @return
     */
    public static Bitmap getUserHead(String username) {
        username = username + ".png";
        byte[] bytes = readFile("user" + username);
        if (bytes == null) {
            return null;
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        }
    }

    /**
     * 获取用户头像图片
     *
     * @param username
     * @return
     */
    public static File getUserHeadForFile(String username) {
        username ="user" + username + ".png";
        File file = getFile(username);
        return file;
    }


    //以下为对文件的操作

    private static byte[] readFile(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(getFile(fileName));
            int len = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((len = fis.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.close();
            fis.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveFile(byte[] bytes, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(getFile(fileName));
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File getFile(String fileName) {
        //现获取目录的字符串格式  然后把字符串转化为文件 判断这个目录是不是存在 不存在要先创建 然后再新建一个可读写的文件（目录加文件名）
        //如果在对文件的操作过程中直接填写目录 如果这个目录不存在 就会报错

        //对目录的操作
        File path = new File(PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        //对文件的操作
        File fileAllName = new File(path, fileName);
        return fileAllName;
    }


}
