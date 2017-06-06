package com.olife.o_life;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.olife.o_life.entity.Feedback;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.StatusBarUtils;

import cn.bmob.v3.BmobUser;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * 关于我的页面的Fragment版
 */
public class AboutMeFragment extends Fragment {
    private View mView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aboutme, null);
        mView = view;
        initToolBar();
        init();
        return view;
    }

    private void init() {

        initUserInfo();
        View view = mView.findViewById(R.id.aboutme_history);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.getCurrentUser(User.class) == null) {
                    startActivity(new Intent(getActivity(), LoginSMSActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), AboutmeHistoryActivity.class));

                }
            }
        });
        View view1 = mView.findViewById(R.id.aboutme_shared);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.getCurrentUser(User.class) == null) {
                    startActivity(new Intent(getActivity(), LoginSMSActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), AboutmeSharedActivity.class));

                }
            }
        });
        View view2 = mView.findViewById(R.id.aboutme_discuss);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.getCurrentUser(User.class) == null) {
                    startActivity(new Intent(getActivity(), LoginSMSActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), AboutmeDiscussActivity.class));

                }
            }
        });

        View view3 = mView.findViewById(R.id.aboutme_feedback);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
            }
        });
        View view4 = mView.findViewById(R.id.aboutme_aboutus);
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), AboutUsActivity.class));

            }
        });
    }

    private void initUserInfo() {

        View view = mView.findViewById(R.id.about_me_userinfo);
        if (BmobUser.getCurrentUser() == null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), LoginSMSActivity.class));
                }
            });
        } else {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
            });
        }


        final ImageView ivimg = (ImageView) mView.findViewById(R.id.aboutme_useriv);
        TextView tvname = (TextView) mView.findViewById(R.id.aboutme_username);
        TextView tvphone = (TextView) mView.findViewById(R.id.aboutme_userphone);

        User user = BmobUser.getCurrentUser(User.class);
        if (user != null) {
            String userimgUrl = user.getImgUrl();
            String name = user.getUsername();
            tvname.setText(name);
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                tvphone.setText(user.getPhone());
            } else if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                tvphone.setText(user.getEmail());
            } else {
                tvphone.setText("未有联系信息");
            }

            if (userimgUrl != null) {
                //显示图片的配置
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.test_icon)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                ImageLoader.getInstance().displayImage(userimgUrl, ivimg, options);

            } else {
                ivimg.setImageResource(R.drawable.test_icon);
            }


//            BmobFile imgfile = new BmobFile("user.png","",userimgUrl);
//            imgfile.download(new DownloadFileListener() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if (e==null){
//                        Toast.makeText(getActivity(), "下载成功，保存路劲"+s, Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onProgress(Integer integer, long l) {
//
//                }
//            });

        } else {
            ivimg.setImageResource(R.drawable.test_icon);
            tvname.setText("未登录，点击登录");
            tvphone.setText("未有联系信息");
        }
    }

    private void initToolBar() {
        final Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.setTitle("我的");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.inflateMenu(R.menu.aboutme_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_item1:
                        startActivity(new Intent(getActivity(), SettingActivity.class));
                        break;
                    default:
                        break;
                }

                return true;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();
        StatusBarUtils.setWindowStatusBarColor(getActivity(),
                ContextCompat.getColor(getApplicationContext(), R.color.bg_blue_deep), false);
    }
}
