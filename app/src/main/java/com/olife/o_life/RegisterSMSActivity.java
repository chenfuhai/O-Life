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

import com.olife.o_life.entity.User;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 手机号注册的版本
 * Created by cuiliang on 2016/11/27.
 */
public class RegisterSMSActivity extends ToolBarBaseActivity {


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


//        btnComfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                final String phone = tvPhone.getEditableText().toString().trim();
//                String code = tvCode.getEditableText().toString().trim();
//
//                boolean cPhone = phone.matches("1\\d{10}");
//                boolean cCode = code.matches("\\d{6}");
//
//                if (phone.isEmpty() || !cPhone) {
//                    Toast.makeText(RegisterSMSActivity.this, "请填写大陆地区11位数字手机号", Toast.LENGTH_SHORT).show();
//                } else if (code.isEmpty() || !cCode) {
//                    Toast.makeText(RegisterSMSActivity.this, "请填写6位数字验证码", Toast.LENGTH_SHORT).show();
//                } else {
//                    User user = new User();
//                    user.setUsername(phone);
//                    user.setPassword("123456");//默认密码
//                    user.setPhone(phone);
//                    user.signOrLogin(code,new SaveListener<User>(){
//
//                        @Override
//                        public void done(User user, BmobException e) {
//                            if (user != null) {
//                                Toast.makeText(RegisterSMSActivity.this, "注册成功，欢迎加入"+user.getUsername(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(RegisterSMSActivity.this, "默认密码为123456 请及时修改", Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(RegisterSMSActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                //BmobError.showErrorMessage(getApplication(), e);
//                            }
//                        }
//                    });
//                      此方法返回空指针异常请不要再试
//                    BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<User>() {
//
//                        @Override
//                        public void done(User user, BmobException e) {
//                            if (user != null) {
//                                Toast.makeText(RegisterSMSActivity.this, "注册成功，欢迎加入"+user.getUsername(), Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(RegisterSMSActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                BmobError.showErrorMessage(getApplication(), e);
//                            }
//                        }
//                    });
//                }
//            }
//        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = tvPhone.getEditableText().toString().trim();
                if (!phone.isEmpty()) {
                    btnGetCode.setEnabled(false);

                    BmobSMS.requestSMSCode(phone, "fuhai", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsid, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterSMSActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                                mSmsId = smsid;
                                handler.sendEmptyMessage(0);


                            } else {
                                //BmobError.showErrorMessage(getApplicationContext(), e);
                                btnGetCode.setEnabled(true);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterSMSActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.register_sms;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");//设置主标题
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


        toolbar.inflateMenu(R.menu.regester_sms_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                switch(menuID){
                    case R.id.action_item1:
                        startActivity(new Intent(RegisterSMSActivity.this,RegisterActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });




    }



}
