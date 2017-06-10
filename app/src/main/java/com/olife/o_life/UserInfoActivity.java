package com.olife.o_life;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.olife.o_life.biz.UserBiz;
import com.olife.o_life.bizImpl.UserBizImpl;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.SDcardTools;
import com.olife.o_life.util.UserUtils;
import com.olife.o_life.view.LoadingDialog;
import com.olife.o_life.view.SelectPicturePopupWindow;

import java.io.File;

/**
 * 用户详细信息页面
 */
public class UserInfoActivity extends ToolBarBaseActivity {


    private ImageView mHead;
    private Bitmap headBitmap;
    private LoadingDialog loadingDialog;

    private TextView mUsername, mUserBrithday, mUserPhone, mUserEmail, mUserPwd;
    private RadioButton mSexman, mSexwomen;
    private RadioGroup mSexGroup;


    private UserBizImpl userBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("用户信息");//设置主标题
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
    }

    private void init() {
        userBiz = new UserBizImpl();

        mHead = (ImageView) findViewById(R.id.userInfo_civ_icon);

        mHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SelectPicturePopupWindow(mHead, UserInfoActivity.this).show();
            }
        });
        loadingDialog = new LoadingDialog(this);

        mUsername = (TextView) findViewById(R.id.userInfo_tv_name);
        mUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoActivity.this, ChangeNameActivity.class));
            }
        });

        mSexman = (RadioButton) findViewById(R.id.userInfo_radio_sexMan);
        mSexwomen = (RadioButton) findViewById(R.id.userInfo_radio_sexWomen);

        mSexGroup = (RadioGroup) findViewById(R.id.userInfo_radioGroup_sex);

        mSexGroup.setOnCheckedChangeListener(new GroupChangeLisenter());


        mUserBrithday = (TextView) findViewById(R.id.userInfo_tv_birthday);
        // 跳转到选择日期对话框
        mUserBrithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                        User user = UserUtils.currentUser();

                        user.setBrithday(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                        userBiz.updateUser(user, new UserBiz.UserDoingLisenter() {
                            @Override
                            public void onStart() {
                                loadingDialog.setMessage("用户信息修改中").show();

                            }

                            @Override
                            public void onSuccess() {
                                loadingDialog.dismiss();
                                Toast.makeText(UserInfoActivity.this, "生日信息修改成功", Toast.LENGTH_SHORT).show();
                                initUserData();
                            }

                            @Override
                            public void onFailed(int e) {
                                loadingDialog.dismiss();
                               // BmobError.showErrorMessage(getApplicationContext(), e);
                            }
                        });
                    }
                }, 2000, 1, 1).show();
            }
        });


        mUserPhone = (TextView) findViewById(R.id.userInfo_tv_phone);
        mUserPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到绑定手机号的界面
                startActivity(new Intent(UserInfoActivity.this, BindPhoneActivity.class));
            }
        });

        mUserEmail = (TextView) findViewById(R.id.userInfo_tv_email);
        mUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到添加邮箱界面
                startActivity(new Intent(UserInfoActivity.this, AddEmailActivity.class));
            }
        });

        //获取个人信息并更新界面


        mUserPwd = (TextView) findViewById(R.id.userInfo_tv_pwd);
        mUserPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到修改密码界面
                startActivity(new Intent(UserInfoActivity.this, ChangePwdActivity.class));
            }
        });
        initUserData();

    }

    @Override
    protected void onResume() {
        initUserData();
        super.onResume();

    }

    /**
     * 获取个人信息并更新界面
     */
    private void initUserData() {
        User cu = UserUtils.currentUser();
        mUsername.setText(cu.getUsername());
        if (cu.getImgUrl() == null) {
            mHead.setImageResource(R.drawable.test_icon);
        } else {

            ImageLoader.getInstance().displayImage(NetConfig.PreUrl+cu.getImgUrl(), mHead);

        }
        if (cu.getSex() != null) {
            mSexGroup.setOnCheckedChangeListener(null);
            if (cu.getSex().equals("true")) {
                mSexman.setChecked(true);

            } else {
                mSexwomen.setChecked(true);
            }
            mSexGroup.setOnCheckedChangeListener(new GroupChangeLisenter());
        }

        if (cu.getBrithday() != null && !cu.getBrithday().isEmpty()) {
            mUserBrithday.setText(cu.getBrithday());
            mUserBrithday.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textGray));
        }

        if (cu.getPhone() != null && !cu.getPhone().isEmpty()) {
            mUserPhone.setText(cu.getPhone());
            mUserPhone.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textGray));
        }

        if (cu.getEmail() != null && !cu.getEmail().isEmpty()) {
            mUserEmail.setText(cu.getEmail());
            mUserEmail.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textGray));
        }
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

                    headBitmap = analysisIntent(data);

                    final User user = UserUtils.currentUser();
                    userBiz.updateUserHead(user, headBitmap, new UserBiz.UserDoingLisenter() {
                        @Override
                        public void onStart() {
                            loadingDialog.setMessage("正在上传头像").show();
                        }

                        @Override
                        public void onSuccess() {
                            loadingDialog.dismiss();
                            Toast.makeText(UserInfoActivity.this, "头像更新成功", Toast.LENGTH_SHORT).show();
                            mHead.setImageBitmap(headBitmap);
                            //本地缓存的头像也跟换下
                            SDcardTools.savaUserHead(headBitmap,user.getUsername());
                        }

                        @Override
                        public void onFailed(int e) {
                            loadingDialog.dismiss();
                            //BmobError.showErrorMessage(getApplicationContext(), e);
                            mHead.setImageResource(R.drawable.test_icon);
                        }
                    });
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
     * 解析itent数据为bitmap
     */

    private Bitmap analysisIntent(Intent data) {

        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            //获取到了截取过后的内容 接下来显示在控件上
            return bitmap;
        } else {
            return null;
        }
    }

    private class GroupChangeLisenter implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            User user = UserUtils.currentUser();

            switch (i) {
                case R.id.userInfo_radio_sexMan:
                    user.setSex("true");

                    userBiz.updateUser(user, new UserBiz.UserDoingLisenter() {
                        @Override
                        public void onStart() {
                            loadingDialog.setMessage("更新用户信息中").show();
                        }

                        @Override
                        public void onSuccess() {
                            loadingDialog.dismiss();
                            Toast.makeText(UserInfoActivity.this, "性别修改成功", Toast.LENGTH_SHORT).show();
                            initUserData();
                        }

                        @Override
                        public void onFailed(int e) {
                            loadingDialog.dismiss();
                            //BmobError.showErrorMessage(getApplicationContext(), e);
                        }
                    });
                    break;
                case R.id.userInfo_radio_sexWomen:
                    user.setSex("false");
                    userBiz.updateUser(user, new UserBiz.UserDoingLisenter() {
                        @Override
                        public void onStart() {
                            loadingDialog.setMessage("更新用户信息中").show();
                        }

                        @Override
                        public void onSuccess() {
                            loadingDialog.dismiss();
                            Toast.makeText(UserInfoActivity.this, "性别修改成功", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailed(int e) {
                            loadingDialog.dismiss();
                           // BmobError.showErrorMessage(getApplicationContext(), e);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }
}



