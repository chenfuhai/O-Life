package com.olife.o_life;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.olife.o_life.util.BmobError;
import com.olife.o_life.util.SDcardTools;
import com.olife.o_life.entity.User;
import com.olife.o_life.view.SelectPicturePopupWindow;
import com.olife.o_life.view.WaitingDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * 普通祖册的版本
 */
public class RegisterActivity extends ToolBarBaseActivity {

    private ImageView head;
    private Bitmap headBitmap;
    private EditText etName, etPass, etConfirmPass, etEmail;
    private Button btnRegister;


    private String pass;
    private String name;
    private String email;
    private String passComfirm;
    private WaitingDialog wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initToolBar();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.register;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("邮箱/用户名注册");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);


        final Drawable upArrow = ContextCompat.getDrawable(getApplicationContext(),
                android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);

        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationIcon(upArrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.inflateMenu(R.menu.regester_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                switch (menuID) {
                    case R.id.action_item1:
                        startActivity(new Intent(RegisterActivity.this,RegisterSMSActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });




    }


    public void init() {

        head = (ImageView) findViewById(R.id.register_head);
        head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new SelectPicturePopupWindow(head, RegisterActivity.this).show();

            }
        });

        etConfirmPass = (EditText) findViewById(R.id.register_pass_comfirm);
        etEmail = (EditText) findViewById(R.id.register_email);
        etName = (EditText) findViewById(R.id.register_username);
        etPass = (EditText) findViewById(R.id.register_pass);


        btnRegister = (Button) findViewById(R.id.register_btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //按下启动一个等待对话框 进行注册联网 注册完成关闭对话框并提示成功 联网失败，注册失败等也关闭对话框并提示错误信息
                wait = new WaitingDialog(RegisterActivity.this, "联网注册中");

                name = etName.getEditableText().toString().trim();
                email = etEmail.getEditableText().toString().trim();
                pass = etPass.getEditableText().toString().trim();
                passComfirm = etConfirmPass.getEditableText().toString().trim();

                //java中不允许跨线程修改UI元素请注意 也不可以在异步线程中使用ToastForZB

                // 判断是否为空并进行注册 注册前判断是否成功 成功了以后上传头像文件 并更新用户类
                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || passComfirm.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "项目为空请完善", Toast.LENGTH_SHORT).show();
                } else if(!pass.equals(passComfirm)){
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请检查", Toast.LENGTH_SHORT).show();
                } else {
                    //进行注册
                    signUp();
                }

            }

        });

        headBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myinfo2);


    }

    /**
     * 更新用户信息 把头像的URL添加到用户类中 并上传
     */

    private void updateUser(final BmobFile headFile) {
        //上传头像文件成功 更新用户信息 把头像文件的Url添加到用户信息中 错误206 登录的用户才能修改自己的信息 需要利用用户名密码登录一次
        BmobUser.loginByAccount(name, pass, new LogInListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                user.setImgUrl(headFile.getFileUrl());
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("fuhai", "done: addUserHeadUrlSuccess" + user.getUsername());
                            wait.dismiss();
                            Toast.makeText(RegisterActivity.this, "注册成功，欢迎 " + user.getUsername(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "已向您的邮箱发送验证邮件，请及时验证邮箱", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Log.i("fuhai", "done: addUserHeadUrlFailed" + e.getMessage() + e.getErrorCode());
                            wait.dismiss();
                            BmobError.showErrorMessage(getApplicationContext(), e);
                        }
                    }
                });
            }
        });
    }

    /**
     * 上传头像
     */
    private void uploadHead(User user) {
        //注册成功的情况下保存头像文件
        SDcardTools.savaUserHead(headBitmap, user.getUsername());
        //上传头像文件
        final BmobFile headFile = new BmobFile(SDcardTools.getUserHeadForFile(user.getUsername()));
        headFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //头像上传成功 得到URL 填入用户类中 并更新用户类
                    updateUser(headFile);
                } else {
                    Log.i("fuhai", "done: headFile uoload failed " + e.getMessage() + e.getErrorCode());
                    wait.dismiss();
                    BmobError.showErrorMessage(getApplicationContext(), e);
                }
            }
        });

    }

    /**
     * 注册用户
     */
    private void signUp() {
        wait.show();
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(pass);
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(final User user, BmobException e) {

                if (e == null) {
                    //注册成功 开始上传头像
                    uploadHead(user);
                } else {
                    wait.dismiss();
                    BmobError.showErrorMessage(getApplicationContext(), e);
                }
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
            this.headBitmap = bitmap;//保存下来 到时候序列化整个用户类并上传
            head.setImageBitmap(bitmap);
        }
    }


}