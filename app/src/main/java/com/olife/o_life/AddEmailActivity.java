package com.olife.o_life;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.olife.o_life.biz.UserBiz;
import com.olife.o_life.bizImpl.UserBizImpl;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.UserUtils;

public class AddEmailActivity extends ToolBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();

        final EditText etemail = (EditText) findViewById(R.id.addemail_email);


        findViewById(R.id.btnComfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etemail.getEditableText().toString().trim();
                User user = new User();
                user.setId(UserUtils.currentUser().getId());
                user.setEmail(email);
                new UserBizImpl().updateUser(user, new UserBiz.UserDoingLisenter() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddEmailActivity.this, "邮箱绑定成功，请尽快验证", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailed(int e) {
                        //BmobError.showErrorMessage(getApplicationContext(), e);
                    }
                });
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_email;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("绑定邮箱");//设置主标题
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
