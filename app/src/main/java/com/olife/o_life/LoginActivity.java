package com.olife.o_life;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.olife.o_life.entity.User;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.SDcardTools;
import com.olife.o_life.util.UserUtils;
import com.olife.o_life.view.WaitingDialog;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 登录 普通的方式
 */
public class LoginActivity extends ToolBarBaseActivity {

    private Button btnLogin, btnRegister;
    private EditText etUsername, etUserpass;
    private AutoCompleteTextView actUsername;
    private WaitingDialog wait;
    private TextView btnForgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolBar();
        init();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登录");//设置主标题
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


        toolbar.inflateMenu(R.menu.login_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                switch (menuID) {
                    case R.id.action_item1:
                        startActivity(new Intent(LoginActivity.this,LoginSMSActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });


    }


    /**
     * 登录
     *
     * @param account
     * @param pass
     */
    private void login(String account, String pass) {

        wait.show();
        User user= new User();
        user.setUsername(account);
        user.setPassword(pass);
        HttpUtils.getInstance().postwithJSON(NetConfig.UserLoginAction,
                GsonGetter.getInstance().getGson().toJson(user),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        User user = GsonGetter.getInstance().getGson().fromJson(result,User.class);

                        Log.i("fuhai", "com.olife.o_life>>LoginActivity>>onSuccessResponse: ");
                        Toast.makeText(LoginActivity.this, "登录成功，欢迎回来 " + user.getUsername(), Toast.LENGTH_LONG).show();
                        //下载并保存用户头像
                        wait.dismiss();
                        //保存下当前登录的用户信息
                        UserUtils.saveCurrentUser(user);
                        dwUserHead(user);
                        finish();
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        wait.dismiss();
                    }
                });
//        BmobUser.loginByAccount(account, pass, new LogInListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(LoginActivity.this, "登录成功，欢迎回来 " + user.getUsername(), Toast.LENGTH_LONG).show();
//                    //下载并保存用户头像
//                    wait.dismiss();
//                    dwUserHead(user);
//                    finish();
//                } else {
//                    wait.dismiss();
//                    //BmobError.showErrorMessage(getApplication(), e);
//                    Log.i("fuhai", "done: login failed" + e.getMessage() + e.getErrorCode());
//                }
//            }
//        });
    }



    /**
     * 下载并保存用户头像 如果本地有头像则不用下载
     */
    private void dwUserHead(User user) {


//        if (!user.getImgUrl().isEmpty()) {
//            String fileName = "user" + user.getUsername() + ".png";
//            BmobFile bmobFile = new BmobFile(fileName, "", user.getImgUrl());
//            bmobFile.download(new File(SDcardTools.getPATH(), bmobFile.getFilename()), new DownloadFileListener() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e == null) {
//                    } else {
//                        Log.i("fuhai", "done: dwheadFileFailed " + e.getMessage() + e.getErrorCode());
//                        //BmobError.showErrorMessage(getApplicationContext(), e);
//                    }
//
//
//                }
//
//                @Override
//                public void onProgress(Integer integer, long l) {
//
//                }
//            });
//        }

    }


    public void init() {

        final CircleImageView head = (CircleImageView) findViewById(R.id.login_civ_head);
        head.setImageResource(R.drawable.icon);


        wait = new WaitingDialog(LoginActivity.this, "登录中。。。");


        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etUsername.getEditableText().toString().trim();
                String pass = etUserpass.getEditableText().toString().trim();
                if (name.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "项目为空请完善", Toast.LENGTH_SHORT).show();
                } else {
                    login(name, pass);
                }
            }
        });

        btnRegister = (Button) findViewById(R.id.login_btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        etUsername = (EditText) findViewById(R.id.login_et_username);
        //actUsername = (AutoCompleteTextView) findViewById(R.id.login_username);
        etUserpass = (EditText) findViewById(R.id.login_et_usepass);
        //String []strs = new String[]{"chenfuhai","chenfuhaiss","chenfuha"};
        //actUsername.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,strs));
        etUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    //如果输入的是空的 那么设置成默认的图片
                    head.setImageResource(R.drawable.icon);
                } else {
                    //这个方法会去查询SD卡里面有没有相对应的图片 如果有就返回一个bitmap 没有就返回空
                    //这个方法是自己写的
                    Bitmap bitmap = SDcardTools.getUserHead(s.toString());
                    if (bitmap != null) {
                        //不是空的 存在这个图片 把头像替换掉
                        head.setImageBitmap(bitmap);
                    } else {
                        //空的 设置成默认头像
                        head.setImageResource(R.drawable.icon);
                    }
                }
            }
        });

        btnForgetPass = (TextView) findViewById(R.id.login_tv_forgetPass);
        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassByPhoneActivity.class));
            }
        });
    }


}
