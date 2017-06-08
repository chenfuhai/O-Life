package com.olife.o_life;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.olife.o_life.util.DataCleanManager;
import com.olife.o_life.util.UserUtils;
import com.olife.o_life.view.LoadingDialog;

/**
 * 设置页面
 * Created by cuiliang on 2016/11/27.
 */
public class SettingActivity extends ToolBarBaseActivity implements View.OnClickListener {
    private RelativeLayout setup_clear,setup_clear_files, setup_push, setup_aboutUs, setup_feedback, setup_update;
    private Button btn_logout;
    private TextView tvClearCount,tvClearCountFile;
    private LoadingDialog mLoadingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();


        //事件处理
        setup_clear = (RelativeLayout) findViewById(R.id.setup_clear);
        setup_clear_files= (RelativeLayout) findViewById(R.id.setup_clearFile);

        setup_push = (RelativeLayout) findViewById(R.id.setup_push);
        setup_aboutUs = (RelativeLayout) findViewById(R.id.setup_aboutUs);
        setup_feedback = (RelativeLayout) findViewById(R.id.setup_feedback);
        setup_update = (RelativeLayout) findViewById(R.id.setup_update);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        tvClearCount = (TextView) findViewById(R.id.setup_clear_count);
        tvClearCountFile = (TextView) findViewById(R.id.setup_clear_count_file);

        setup_clear.setOnClickListener(this);
        setup_clear_files.setOnClickListener(this);
        setup_push.setOnClickListener(this);
        setup_aboutUs.setOnClickListener(this);
        setup_feedback.setOnClickListener(this);
        setup_update.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        tvClearCount.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
        tvClearCountFile.setText(DataCleanManager.getTotalFilesSize(getApplicationContext()));

        mLoadingDialog = new LoadingDialog(SettingActivity.this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    private void initToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");//设置主标题
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup_clear: {
                //具体操作
                DataCleanManager.clearAllCache(getApplicationContext());
                tvClearCount.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
                Toast.makeText(getApplication(), "缓存已清除", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.setup_clearFile: {
                //具体操作
                DataCleanManager.clearAllFiles(getApplicationContext());
                tvClearCountFile.setText(DataCleanManager.getTotalFilesSize(getApplicationContext()));
                Toast.makeText(getApplication(), "数据已清除", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.setup_push: {
                //具体操作
                Toast.makeText(getApplication(), "敬请期待", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.setup_aboutUs: {
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));

            }
            break;
            case R.id.setup_feedback: {
                startActivity(new Intent(SettingActivity.this, FeedBackActivity.class));

            }
            break;
            case R.id.setup_update: {
                //具体操作
                mLoadingDialog.setMessage("检查更新中");
                mLoadingDialog.show();
                //BmobUpdateAgent.setUpdateListener(new UpdateListener());
                //BmobUpdateAgent.forceUpdate(SettingActivity.this);

            }
            break;
            case R.id.btn_logout: {
                //具体操作
                if (UserUtils.currentUser()==null){
                    Toast.makeText(this, "您未登录！", Toast.LENGTH_SHORT).show();
                }else {
                    //BmobUser.logOut();
                    UserUtils.removeCurrentUser();
                    Toast.makeText(this, "您已成功推出登录", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            break;

            default:
                break;
        }
    }

//    private class UpdateListener implements BmobUpdateListener {
//
//        @Override
//        public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
//            if (updateStatus == UpdateStatus.Yes) {
//                //版本有更新
//
//
//            } else if (updateStatus == UpdateStatus.No) {
//                Toast.makeText(SettingActivity.this, "版本无更新", Toast.LENGTH_SHORT).show();
//            } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
//                Toast.makeText(SettingActivity.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
//            } else if (updateStatus == UpdateStatus.IGNORED) {
//                Toast.makeText(SettingActivity.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
//            } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
//                Toast.makeText(SettingActivity.this, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
//            } else if (updateStatus == UpdateStatus.TimeOut) {
//                Toast.makeText(SettingActivity.this, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
//            }
//
//            mLoadingDialog.dismiss();
//        }
//
//    }

}
