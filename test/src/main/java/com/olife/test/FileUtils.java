package com.olife.test;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by chenfuhai on 2017/6/5 0005.
 */

public class FileUtils {
    /**
     * bitmap转为file
     * @param bitmap
     * @return
     */
    public File Bitmap2File(Bitmap bitmap) {
        File sdCardDir = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDir, UUID.randomUUID()+".jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



}
