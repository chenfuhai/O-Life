package com.olife.o_life;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * 关于我的页面的Activity版
 */
public class AboutMeActivity extends ToolBarBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.aboutme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolBar();

        init();

    }

    private void init() {

        findViewById(R.id.about_me_userinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutMeActivity.this,UserInfoActivity.class));
            }
        });
    }


    private void initToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.inflateMenu(R.menu.aboutme_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_item1:
                        startActivity(new Intent(AboutMeActivity.this,SettingActivity.class));
                        break;
                    default:
                        break;
                }

                return true;
            }
        });


    }
}
