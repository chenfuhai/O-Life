package com.olife.o_life;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.olife.o_life.biz.UserBiz;
import com.olife.o_life.bizImpl.UserBizImpl;
import com.olife.o_life.entity.User;

import cn.bmob.v3.BmobUser;


public class BindPhoneActivity extends ToolBarBaseActivity {

    private TextView tvPhone, tvCode;
    private Button btnComfirm, btnGetCode;


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
                //如果当前用户已经绑定了手机号或者其手机号跟想要绑定的一致 则无需绑定 提示
                //用户没有绑定手机号 发送验证码以后 验证通过则更新用户类 并发广播
                final String phone = tvPhone.getEditableText().toString().trim();
                String code = tvCode.getEditableText().toString().trim();

                boolean cPhone = phone.matches("1\\d{10}");
                boolean cCode = code.matches("\\d{6}");

                if (phone.isEmpty() || !cPhone) {
                    Toast.makeText(BindPhoneActivity.this, "请填写大陆地区11位数字手机号", Toast.LENGTH_SHORT).show();
                } else if (code.isEmpty() || !cCode) {
                    Toast.makeText(BindPhoneActivity.this, "请填写6位数字验证码", Toast.LENGTH_SHORT).show();
                } else {
                    User user = BmobUser.getCurrentUser(User.class);
                    //如果当前的用户已经有保存了手机号并且与现在将要绑定的手机号相同 那么不要绑定
                    if (user.getPhone() != null && user.getPhone().equals(phone)) {
                        Toast.makeText(BindPhoneActivity.this, "手机号与原手机号一致，无需绑定", Toast.LENGTH_SHORT).show();
                    } else {
                        new UserBizImpl().verifySmsCode(phone, code, new UserBiz.UserDoingLisenter() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess() {
                                Toast.makeText(BindPhoneActivity.this, "手机号绑定成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailed(int e) {
                                //BmobError.showErrorMessage(getApplicationContext(), e);
                            }
                        });
                    }
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
                            Toast.makeText(BindPhoneActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onFailed(int e) {
                            //BmobError.showErrorMessage(getApplicationContext(), e);
                            btnGetCode.setEnabled(true);
                        }
                    });
                } else {
                    Toast.makeText(BindPhoneActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindphone;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("绑定手机");//设置主标题
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
}
