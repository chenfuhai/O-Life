package com.olife.o_life;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.olife.o_life.biz.FeedBackBiz;
import com.olife.o_life.bizImpl.FeedBackBizImpl;
import com.olife.o_life.entity.Feedback;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.BmobError;
import com.olife.o_life.view.LoadingDialog;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class FeedBackActivity extends ToolBarBaseActivity {


    private Button btnComfirm;
    private EditText etMessage, etQQ, etEmail, etPhone;
    private LoadingDialog dialog;
    private FeedBackBizImpl feedBackBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        init();
    }

    private void init() {
        feedBackBiz = new FeedBackBizImpl();

        btnComfirm = (Button) findViewById(R.id.backmessage_btnComfirm);
        etMessage = (EditText) findViewById(R.id.backmessage_etMessage);
        etQQ = (EditText) findViewById(R.id.backmessage_etQQ);
        etEmail = (EditText) findViewById(R.id.backmessage_etEmail);
        etPhone = (EditText) findViewById(R.id.backmessage_etPhone);

        dialog = new LoadingDialog(FeedBackActivity.this);
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = etMessage.getEditableText().toString().trim();
                if (message == null || message.isEmpty()) {
                    Toast.makeText(FeedBackActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                } else {
                    btnComfirm.setEnabled(false);
                    dialog.setMessage("处理反馈意见中");
                    dialog.show();
                    Feedback feedback = new Feedback( message);
                    if (BmobUser.getCurrentUser(User.class)!=null){
                        feedback.setUserId(BmobUser.getCurrentUser(User.class).getObjectId());
                    }
                    if (!etQQ.getEditableText().toString().trim().isEmpty()){
                        feedback.setQQ(etQQ.getEditableText().toString().trim());
                    }
                    if (!etEmail.getEditableText().toString().trim().isEmpty()){
                        feedback.setEmail(etEmail.getEditableText().toString().trim());
                    }
                    if (!etPhone.getEditableText().toString().trim().isEmpty()){
                        feedback.setPhone(etPhone.getEditableText().toString().trim());
                    }
                    feedBackBiz.submitFeedBack(feedback, new FeedBackBiz.FeedbackDoingLisenter() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            btnComfirm.setEnabled(true);
                            Toast.makeText(FeedBackActivity.this, "提交成功，感谢您的反馈", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailed(BmobException e) {
                            dialog.dismiss();
                            btnComfirm.setEnabled(true);
                            BmobError.showErrorMessage(getApplicationContext(), e);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("意见反馈");//设置主标题
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
