package com.olife.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ImageView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.text);

        String JSON = "{'abc6':'123','abc5':'123','abc4':'123','abc3':'123','abc2':'123','abc1':'123'}";
        Toast.makeText(MainActivity.this, "1231", Toast.LENGTH_SHORT).show();

        head = (ImageView) findViewById(R.id.imageView);
        head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new SelectPicturePopupWindow(head, MainActivity.this).show();

            }
        });


    }


    /**
     * 处理返回这个Aty的数据 之前的选择照片都要在这里处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {


            switch (requestCode) {
                case SelectPicturePopupWindow.REQUESTCODE_GALLERY:
                    photoZoom(data.getData());
                    break;
                case SelectPicturePopupWindow.REQUESTCODE_PHOTO:
                    File temp = new File(Environment.getExternalStorageDirectory(),
                            SelectPicturePopupWindow.IMAGE_FILE_NAME);

                    photoZoom(Uri.fromFile(temp));
                    // 有问题  知道了 调用相机的因为确实不会传进来 不会通过Intent方式传东西进来
                    // 只是会在外部存好了一个文件 把那个文件拿回来就好了
                    //这也是data为空的原因
                    break;
                case SelectPicturePopupWindow.REQUESTCODE_CUTTING:
                    showViewData(data);
                    break;

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void photoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高

        intent.putExtra("return-data", true);
        startActivityForResult(intent, SelectPicturePopupWindow.REQUESTCODE_CUTTING);
    }

    /**
     * 显示图片
     *
     * @param data
     */
    private void showViewData(Intent data) {
        //获得uri 把uri解析为流 把流解析成数组
        //把uri解析为字节数组 将字节数组转化为imgeView可以识别的bitmap 设置bitmap
        //使用MediaStore.Images.Media.getBitmap
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            File file = saveBitmapFile(bitmap);
            HttpUtils.getInstance().postwithFile(NetConfig.TestAction, "img", file, null, new HttpUtils.SuccessListener() {
                @Override
                public void onSuccessResponse(String result) {
                    Log.i("fuhai", "com.olife.test>>MainActivity>>onSuccessResponse: " + result);
                }
            }, new HttpUtils.FailedListener() {
                @Override
                public void onFialed(int connectCode) {
                    Log.i("fuhai", "com.olife.test>>MainActivity>>onFialed: " + connectCode);
                }
            });
        }
    }


    public File saveBitmapFile(Bitmap bitmap) {
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


