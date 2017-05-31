package com.olife.o_life.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.olife.o_life.R;

/**
 * Created by wuguofei on 2016/12/12.
 */

public class LoadingDialog extends Dialog {
    private TextView tv_text;


    public LoadingDialog(Context context) {
        super(context, R.style.Transparent_Dialog);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.loading);
        tv_text = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);
    }

    public LoadingDialog setMessage(String message) {
        tv_text.setText(message);
        return this;
    }
}
