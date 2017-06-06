package com.olife.o_life;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.olife.o_life.biz.UserBiz;
import com.olife.o_life.bizImpl.UserBizImpl;


/**
 * 登录 用手机验证码登录
 * Created by cuiliang on 2016/11/27.
 */
public class LoginSMSActivity extends ToolBarBaseActivity {

    private TextView tvPhone, tvCode;
    private Button btnComfirm, btnGetCode;
    private Integer mSmsId;//获取到的验证码信息id 用来查询本次短信发送的状态


    private Handler handler = new Handler() {
        private int i = 60;

        @Override
        public void handleMessage(Message msg) {
            btnGetCode.setText("重新发送（" + i + "）");
            i--;
            if (i >= 0) {
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                i = 60;
                btnGetCode.setEnabled(true);
                btnGetCode.setText("重新发送");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        tvCode = (TextView) findViewById(R.id.phoneNum_etcode);
        tvPhone = (TextView) findViewById(R.id.phoneNum_etphone);
        btnComfirm = (Button) findViewById(R.id.phoneNum_btnComfirm);
        btnGetCode = (Button) findViewById(R.id.phoneNum_btnCode);


        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phone = tvPhone.getEditableText().toString().trim();
                String code = tvCode.getEditableText().toString().trim();

                boolean cPhone = phone.matches("1\\d{10}");
                boolean cCode = code.matches("\\d{6}");

                if (phone.isEmpty() || !cPhone) {
                    Toast.makeText(LoginSMSActivity.this, "请填写大陆地区11位数字手机号", Toast.LENGTH_SHORT).show();
                } else if (code.isEmpty() || !cCode) {
                    Toast.makeText(LoginSMSActivity.this, "请填写6位数字验证码", Toast.LENGTH_SHORT).show();
                } else {
                    new UserBizImpl().loginBySMSCode(phone, code, new UserBiz.UserDoingLisenter() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginSMSActivity.this, "登录成功，欢迎回来", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailed(int e) {
                            //BmobError.showErrorMessage(getApplicationContext(), e);
                        }
                    });
                }
            }
        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = tvPhone.getEditableText().toString().trim();
                if (!phone.isEmpty()) {
                    btnGetCode.setEnabled(false);

                    new UserBizImpl().requestSMSCode(phone, new UserBiz.UserDoingLisenter() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginSMSActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onFailed(int e) {
                           // BmobError.showErrorMessage(getApplicationContext(), e);
                            btnGetCode.setEnabled(true);
                        }
                    });
                } else {
                    Toast.makeText(LoginSMSActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.loginsms_tv_wenti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSMSActivity.this, RegisterSMSActivity.class));
                finish();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_loginsms;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("短信登录");//设置主标题
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


        toolbar.inflateMenu(R.menu.login_sms_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                switch (menuID) {
                    case R.id.action_item1:
                        startActivity(new Intent(LoginSMSActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });


    }


}
