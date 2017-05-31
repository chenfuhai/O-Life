package com.olife.o_life;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.olife.o_life.adapter.OneKeyRecyclerAdapter;
import com.olife.o_life.adapter.OnekeyCheckViewPagerAdapter;
import com.olife.o_life.biz.OnekeySharedMessageBiz;
import com.olife.o_life.biz.OnekeyResultBiz;
import com.olife.o_life.bizImpl.OnekeySharedMessageBizImpl;
import com.olife.o_life.bizImpl.OnekeyResultBizImpl;
import com.olife.o_life.entity.OnekeyResultRecord;
import com.olife.o_life.entity.OnekeyResultLocal;
import com.olife.o_life.entity.OnekeySharedMessage;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.BmobError;
import com.olife.o_life.util.LocationUtils;
import com.olife.o_life.util.StatusBarUtils;
import com.olife.o_life.view.LoadingDialog;
import com.olife.pointview.PointViewFull;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

import static android.R.id.list;
import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * 一件检测的fragment版本
 */

public class OneKeyCheckFragment extends Fragment {

    private OneKeyRecyclerAdapter mAdapter;

    private View mView;
    private PointViewFull pointView;
    private int mBackgroundColor;

    private ViewPager mViewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onekye_check, null);
        mView = view;


        initViewPager();

        mView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyResultLocal r = new OnekeyResultLocal("苯检测完成",
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_login), true);
                mAdapter.remove(0);
                mAdapter.add(r, 0);
                mAdapter.notifyItemChanged(0);
                pointView.setCurrText("准备下一项中");
                pointView.setCurrScore(79);
            }
        });

        mView.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyResultLocal r = new OnekeyResultLocal("PM2.5检测完成",
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_login), true);
                mAdapter.remove(1);
                mAdapter.add(r, 1);
                mAdapter.notifyItemChanged(1);
                pointView.setCurrText("检测完成");
                pointView.setCurrScore(59);
                pointView.waitingStop();

                final OnekeyResultRecord resultRecord = new OnekeyResultRecord(59, BmobUser.getCurrentUser(User.class).getObjectId());
                LocationUtils.getmLocationUtil(getApplicationContext()).startLocation(new LocationUtils.LocationDoingLisenter() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(AMapLocation location) {
                        resultRecord.setLat(location.getLatitude());
                        resultRecord.setLng(location.getLongitude());

                    }

                    @Override
                    public void onFailed() {

                    }

                    @Override
                    public void onError(LocationUtils.LocationDoingError e) {

                    }

                    @Override
                    public void onFinish() {
                        new OnekeyResultBizImpl().saveOnekeyResult(resultRecord, new OnekeyResultBiz.OnekeyDoingLisenter() {
                            @Override
                            public void onStart() {
                                Toast.makeText(getActivity(), "保存结果数据", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(BmobException e) {
                                BmobError.showErrorMessage(getApplicationContext(), e);
                            }
                        });
                    }
                });

            }
        });

        mView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyResultLocal r = new OnekeyResultLocal("苯检测中",
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_login), false);
                mAdapter.add(r, OneKeyRecyclerAdapter.LAST_POSITION);

                pointView.setCurrText("检测苯含量");

            }
        });
        mView.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyResultLocal r = new OnekeyResultLocal("PM2.5检测中",
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_login), false);
                mAdapter.add(r, OneKeyRecyclerAdapter.LAST_POSITION);
                pointView.setCurrText("检测PM25");
            }
        });

        pointView = (PointViewFull) mView.findViewById(R.id.one_key_pointView);
        pointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointView.waitingStart();
                pointView.setCurrText("连接检测设备");
            }
        });

        pointView.setCurrActivity(getActivity());

        pointView.setOnColorChangerListener(new PointViewFull.ColorChangeLisenter() {
            @Override
            public void colorChanged(int color) {
                OneKeyCheckFragment.this.mBackgroundColor = color;
            }
        });


        return view;
    }

    private void initViewPager() {
        mViewPager = (ViewPager) mView.findViewById(R.id.one_key_viewpager);
        View itemInfo = View.inflate(getApplicationContext(), R.layout.item_viewpager_onekeycheck_info, null);
        View itemAnalysis = View.inflate(getActivity(), R.layout.item_viewpager_onekeycheck_analysis, null);


        RecyclerView recyclerView = (RecyclerView) itemInfo.findViewById(R.id.one_key_recycler_view);
        recyclerView.setHasFixedSize(true);
        //设置布局管理器 添加移除动画 添加分隔符 设置适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SlideInDownAnimator animator = new SlideInDownAnimator(new OvershootInterpolator(0f));
        recyclerView.setItemAnimator(animator);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new OneKeyRecyclerAdapter(null);

        recyclerView.setAdapter(mAdapter);


        FloatingActionButton btnShared = (FloatingActionButton) itemAnalysis.findViewById(R.id.floatingActionButton);
        btnShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.getCurrentUser(User.class) == null) {
                    startActivity(new Intent(getActivity(), LoginSMSActivity.class));
                } else {
                    //分享
                    sharedOnekeyResult();
                }
            }
        });

        TextView tv= (TextView) mView.findViewById(R.id.text);


        List<View> list = new ArrayList<>();
        list.add(itemInfo);
        list.add(itemAnalysis);
        mViewPager.setAdapter(new OnekeyCheckViewPagerAdapter(list));


    }

    /**
     * 分享检测的数据到周边
     */

    private void sharedOnekeyResult() {
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());

        //获取最后一次检测数据
        new OnekeyResultBizImpl().findLastOnkeyResultByUserId(BmobUser.getCurrentUser().getObjectId(),
                new OnekeyResultBiz.FindLastDoingLisenter() {
                    @Override
                    public void onStart() {
                        loadingDialog.setMessage("整合数据分享中").show();
                    }

                    @Override
                    public void onSuccess(final OnekeyResultRecord record) {
                        if (record == null) {
                            Toast.makeText(getApplicationContext(), "您还未检测，请检测后分享您的数据", Toast.LENGTH_SHORT).show();
                        } else {
                            //开始定位
                            LocationUtils.getmLocationUtil(getApplicationContext()).startLocation(new LocationUtils.LocationDoingLisenter() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(AMapLocation location) {
                                    //定位成功以后 开始上传数据发布分享
                                    Double lat,lng;
                                    if (record.getLng()==null || record.getLat() == null){
                                        lat = location.getLatitude();
                                        lng = location.getLongitude();
                                    }else {
                                        lat = location.getLatitude();
                                        lng = location.getLongitude();
                                    }

                                    OnekeySharedMessage sharedMessage = new OnekeySharedMessage(record.getResultMark(),
                                            record.getUserId(),lat, lng);
                                    sharedMessage.setBen(10);
                                    sharedMessage.setPm2_5(50);
                                    sharedMessage.setResultMark(88);
                                    sharedMessage.setSuggest("科学建议");
                                    sharedMessage.setProvince("省份");
                                    sharedMessage.setCity("城市");
                                    sharedMessage.setDistrict("地区");
                                    sharedMessage.setStreet("街道");
                                    sharedMessage.setStreetNum("街道门牌号");
                                    sharedMessage.setUsername("用户姓名");
                                    sharedMessage.setUserage("20");
                                    sharedMessage.setUserImgUrl("头像的URL地址");
                                    sharedMessage.setUserphone("13247808200");
                                    sharedMessage.setUsersex("男");
                                    sharedMessage.setUseremail("email");
                                    sharedMessage.setDriverId("设备编号");

                                    new OnekeySharedMessageBizImpl().shareUserResult(sharedMessage, new OnekeySharedMessageBiz.DiscoverDoingLisenter() {
                                        @Override
                                        public void onStart() {

                                        }

                                        @Override
                                        public void onSuccess() {
                                            //保存成功 即发布成功
                                            Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailed(BmobException e) {
                                            BmobError.showErrorMessage(getApplicationContext(), e);
                                        }
                                    });
                                }

                                @Override
                                public void onFailed() {
                                    Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(LocationUtils.LocationDoingError e) {
                                    Toast.makeText(getApplicationContext(), "定位失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFinish() {

                                }
                            });
                        }
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onFailed(BmobException e) {
                        BmobError.showErrorMessage(getApplicationContext(), e);
                        loadingDialog.dismiss();
                    }
                });


    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.setTitle("一键检测");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @Override
    public void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(getActivity(), getBackgroundColor(), false);
    }
}
