package com.olife.o_life;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 忘记密码用邮箱找回的activity
 */
public class ForgetPassByEmailActivity extends ToolBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolBar();

        final TextView tvEmail = (TextView) findViewById(R.id.tvemail);
        final TextView tvMessage = (TextView) findViewById(R.id.message);


        final Button btnComfirm = (Button) findViewById(R.id.btnComfirm);
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(ForgetPassByEmailActivity.this, "输入为空，请检查", Toast.LENGTH_SHORT).show();
                }else {
                    btnComfirm.setEnabled(false);
                    BmobUser.resetPasswordByEmail(tvEmail.getText().toString().trim(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                btnComfirm.setEnabled(false);
                                tvMessage.setText("已向您的邮箱 "+tvEmail.getText().toString().trim()+"发送重置密码邮件\n请在网页端完成密码重置");
                            }else{
                                btnComfirm.setEnabled(true);
                                //BmobError.showErrorMessage(getApplicationContext(),e);
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd_email;
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

        toolbar.inflateMenu(R.menu.forget_pwd_email_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuID = item.getItemId();
                switch (menuID) {
                    case R.id.action_item1:
                        startActivity(new Intent(ForgetPassByEmailActivity.this,ForgetPassByPhoneActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });




    }



}
