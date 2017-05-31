package com.olife.o_life;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.olife.o_life.biz.UserBiz;
import com.olife.o_life.bizImpl.UserBizImpl;
import com.olife.o_life.util.BmobError;

import cn.bmob.v3.exception.BmobException;

public class ChangePwdActivity extends ToolBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final EditText etoldPwd = (EditText) findViewById(R.id.changepwd_et_oldpwd);
        final EditText etPwd = (EditText) findViewById(R.id.changepwd_et_newpwd);
        final EditText etRepeatPwd = (EditText) findViewById(R.id.changepwd_et_renewpwd);

        findViewById(R.id.btnComfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwdold = etoldPwd.getEditableText().toString().trim();
                String pwd = etPwd.getEditableText().toString().trim();
                String pwdR = etRepeatPwd.getEditableText().toString().trim();
                if (!pwd.equals(pwdR)){
                    Toast.makeText(ChangePwdActivity.this, "两次输入的密码不一样,请检查", Toast.LENGTH_SHORT).show();
                }else {
                   new UserBizImpl().changePwd(pwdold, pwd, new UserBiz.UserDoingLisenter() {
                       @Override
                       public void onStart() {

                       }

                       @Override
                       public void onSuccess() {
                           Toast.makeText(ChangePwdActivity.this, "密码修改成功，请牢记您的密码", Toast.LENGTH_SHORT).show();
                            finish();
                       }

                       @Override
                       public void onFailed(BmobException e) {
                            BmobError.showErrorMessage(getApplicationContext(),e);
                       }
                   });
                }
            }
        });

        initToolBar();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("修改密码");//设置主标题
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changepwd;
    }
}
