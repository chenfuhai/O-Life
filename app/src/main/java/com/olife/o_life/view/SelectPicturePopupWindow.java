package com.olife.o_life.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

import com.olife.o_life.R;

import java.io.File;

/**
 * 选择照片从相册还是照相弹出的框
 */

public class SelectPicturePopupWindow extends PopupWindow {
	public static final int REQUESTCODE_PHOTO = 110;
	public static final int REQUESTCODE_GALLERY = 111;
	public static final int REQUESTCODE_CUTTING = 112;
	public static final  String IMAGE_FILE_NAME = "temp";
	
	
	private View parent;
	private Activity mContext;
	private View view;
	
	private Button btnCancel;
	private Button btnPhoto;
	private Button btnGallery;
	/**
	 * //注意这里的参数 第一个 要调用父窗口的getWindowToken() 所以只要是父窗口底下的控件就可以了 
	 * @param
	 */
	public SelectPicturePopupWindow(View widget,Activity currentAty) {
		this.parent = widget;
		this.mContext = currentAty;
		init();

	}

	public void show() {
		//显示在底部
		if (!this.isShowing()) {
			this.showAtLocation(parent, Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
		}else {
			dismiss();
		}
		
	}

	private void init() {
		this.view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.select_picture_pop, null);
		setContentView(view);
		
		//设置宽高
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		   // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        this.setOutsideTouchable(true);  
        // 实例化一个ColorDrawable颜色为半透明    
        ColorDrawable dw = new ColorDrawable(0x44000000);     
        // 设置弹出窗体的背景      
        this.setBackgroundDrawable(dw);  
        
        // 刷新状态  
        this.update();  
        //弹出的动画效果
        setAnimationStyle(R.style.select_pop_amin);
        
        MyitemClickListener listener =new MyitemClickListener();

        btnCancel =(Button) view.findViewById(R.id.pop_head_cancel);
		btnCancel.setOnClickListener(listener);
		
		btnGallery = (Button) view.findViewById(R.id.pop_head_gallery);
		btnGallery.setOnClickListener(listener);
		
		btnPhoto = (Button) view.findViewById(R.id.pop_head_photo);
		btnPhoto.setOnClickListener(listener);
	}
	
	private class MyitemClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			dismiss();
			switch (v.getId()) {
				//从图库选择
			case R.id.pop_head_gallery:
				Intent intent1 = new Intent(Intent.ACTION_PICK);
				intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				mContext.startActivityForResult(intent1, REQUESTCODE_GALLERY);
				break;
			case R.id.pop_head_photo:
				//照相
				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//下面这句指定调用相机拍照后的照片存储的路径
				
				intent2.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
				mContext.startActivityForResult(intent2, REQUESTCODE_PHOTO);
				break;
			case R.id.pop_head_cancel:
				//取消
				break;
			}
		}
		
	}
}
