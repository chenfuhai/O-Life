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

/**
 * 忘记密码用手机找回的第一步的acitivty
 */
public class ForgetPassByPhoneActivity extends ToolBarBaseActivity {

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
        btnComfirm = (Button) findViewById(R.id.btnComfirm);
        btnGetCode = (Button) findViewById(R.id.phoneNum_btnCode);


        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phone = tvPhone.getEditableText().toString().trim();
                String code = tvCode.getEditableText().toString().trim();

                boolean cPhone = phone.matches("1\\d{10}");
                boolean cCode = code.matches("\\d{6}");

                if (phone.isEmpty() || !cPhone) {
                    Toast.makeText(ForgetPassByPhoneActivity.this, "请填写大陆地区11位数字手机号", Toast.LENGTH_SHORT).show();
                } else if (code.isEmpty() || !cCode) {
                    Toast.makeText(ForgetPassByPhoneActivity.this, "请填写6位数字验证码", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(ForgetPassByPhoneActivity.this,ForgetPassByPhoneCheckActivity.class);
                    i.putExtra("code",code);
                    startActivity(i);
                    finish();
                }
            }
        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"暂不支持敬请期待",Toast.LENGTH_SHORT).show();
//                String phone = tvPhone.getEditableText().toString().trim();
//                if (!phone.isEmpty()) {
//                    btnGetCode.setEnabled(false);
//
//                    BmobSMS.requestSMSCode(phone, "fuhai", new QueryListener<Integer>() {
//                        @Override
//                        public void done(Integer smsid, BmobException e) {
//                            if (e == null) {
//                                Toast.makeText(ForgetPassByPhoneActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
//                                mSmsId = smsid;
//                                handler.sendEmptyMessage(0);
//
//
//                            } else {
//                                //BmobError.showErrorMessage(getApplicationContext(), e);
//                                btnGetCode.setEnabled(true);
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(ForgetPassByPhoneActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd_phonecode;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("忘记密码");//设置主标题
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


        toolbar.inflateMenu(R.menu.forget_pwd_phonecode_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                switch (menuID) {
                    case R.id.action_item1:
                        startActivity(new Intent(ForgetPassByPhoneActivity.this,ForgetPassByEmailActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });






    }



}
