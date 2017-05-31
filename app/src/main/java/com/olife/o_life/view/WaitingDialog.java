package com.olife.o_life.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.olife.o_life.R;


/**
 * 需要一個對應的佈局 這個對話框   還要實現ONcreate 注意這裡的context要傳入Activity 
 * 因為只有activit才可以添加窗體 不然會報錯
 * @author chenfuhai
 *
 */
public class WaitingDialog extends ProgressDialog {

	
	private AnimationDrawable mAnimation;
	private ImageView mImageView;
	private TextView mTextView;
	private String tip;
	
	public WaitingDialog(Activity context,String tip) {
		super(context);
		setCanceledOnTouchOutside(false);
		this.tip = tip;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	private void init() {
		setContentView(R.layout.dialog_waiting);
		mImageView = (ImageView) findViewById(R.id.loadingIv);
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		
		mTextView =  (TextView) findViewById(R.id.lodingTv);
		if (tip != null) {
			mTextView.setText(tip);
		}
	}

	
	public void setTip(String tip){
		if (tip != null) {
			mTextView.setText(tip);
		}
	}
	@Override
	public void onBackPressed() {
		//用戶按返回無效
	}
}
