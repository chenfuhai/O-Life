package com.olife.o_life;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.olife.o_life.util.BmobError;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 忘记密码用手机找回的第二步的activity
 */
public class ForgetPassByPhoneCheckActivity extends ToolBarBaseActivity {


    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCode = "";
        initToolBar();

        Intent i = getIntent();
        if (i != null) {
            mCode = i.getStringExtra("code");
        }


        final EditText etPwd = (EditText) findViewById(R.id.forgetpwd_reset_newpwd);
        final EditText etRepeatPwd = (EditText) findViewById(R.id.forgetpwd_reset_repeatpwd);



        findViewById(R.id.btnComfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String pwd = etPwd.getEditableText().toString().trim();
                String pwdR = etRepeatPwd.getEditableText().toString().trim();
                if (!pwd.equals(pwdR)){
                    Toast.makeText(ForgetPassByPhoneCheckActivity.this, "两次输入的密码不一样,请检查", Toast.LENGTH_SHORT).show();
                }else {
                    BmobUser.resetPasswordBySMSCode(mCode,pwd, new UpdateListener() {

                        @Override
                        public void done(BmobException ex) {
                            if(ex==null){
                                Toast.makeText(ForgetPassByPhoneCheckActivity.this, "密码重置成功,请牢记您的密码", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                BmobError.showErrorMessage(getApplicationContext(),ex);
                            }
                        }
                    });
                }
            }
        });



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd_phonecode_check;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("重置密码");//设置主标题
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
