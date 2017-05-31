package com.olife.o_life;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.olife.o_life.adapter.DiscoveryDiscussAdapter;
import com.olife.o_life.biz.OnekeySharedDiscussionBiz;
import com.olife.o_life.biz.OnekeySharedMessageBiz;
import com.olife.o_life.bizImpl.OnekeySharedDiscussionBizImpl;
import com.olife.o_life.bizImpl.OnekeySharedMessageBizImpl;
import com.olife.o_life.entity.OnekeySharedDisc;
import com.olife.o_life.entity.OnekeySharedMessage;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.BmobError;
import com.olife.o_life.util.LocationUtils;
import com.olife.o_life.util.StatusBarUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import static cn.bmob.v3.Bmob.getApplicationContext;

//import static com.olife.o_life.R.id.map_discuss_lv;


/**
 * 周边空气的fragment版
 * Created by wuguofei on 2016/11/30.
 */
public class DiscoveryFragment extends Fragment implements View.OnClickListener {

    private MapView mMapView = null;
    private AMap aMap;
    private LocationManager locationManager;
    private double lat;
    private double lon;
    private View view_info, view_discuss, view;
    private TextView tv_map_info;
    List<java.util.Map<String, Object>> datas;
    List<OnekeySharedDisc> datas1;
    private PullLoadMoreRecyclerView rv;
    private EditText map_info_et;
    private String messageId,userId;
    //List<OnekeySharedMessage> messagedata;
    //OnekeySharedDisc Oneke
    String c;

    /**
     * 信息部分
     */
    private ViewPager map_viewPager;
    private ArrayList<View> map_pageview;
    // 当前页编号
    private int currIndex = 0;
    private DiscoveryDiscussAdapter discussAdapter;
    private int mCount = 1;



    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    // df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    Log.v("pcw", "lat : " + lat + " lon : " + lon);

                    // 设置当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("当前位置");
                    markerOptions.visible(true);
                    markerOptions.draggable(true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map_mark1));
                    markerOptions.icon(bitmapDescriptor);
                    aMap.addMarker(markerOptions);

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initToolBar();
        return mView;
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.setTitle("周边空气");//设置主标题
        toolbar.setTitleTextColor(Color.WHITE);
    }


    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ShortCut.crateShortCut(this,R.mipmap.ic_launcher,R.string.app_name);


        mView = View.inflate(getActivity(), R.layout.discovery, null);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取地图控件引用
        mMapView = (MapView) mView.findViewById(R.id.map);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化
        init();


        //GPRS提供的定位信息改变
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 8, new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // 使用GPRS提供的定位信息来更新位置
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                updatePosition(locationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                updatePosition(location);
            }
        });



    }

    /**
     * 地图部分
     */
    //初始化AMap对象
    private void init() {
        map_viewPager = (ViewPager) mView.findViewById(R.id.map_viewPager);

        //查找布局文件用LayoutInflater.inflate
        //地图信息显示部分
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view_info = inflater.inflate(R.layout.item_viewpager_discovery_info, null);
        tv_map_info = (TextView) view_info.findViewById(R.id.tv_map_info);
        map_info_et = (EditText) view_info.findViewById(R.id.map_info_et);
        ImageButton btn2 = (ImageButton) view_info.findViewById(R.id.map_info_btn);
        btn2.setOnClickListener(this);
        //评论区部分
        view_discuss = inflater.inflate(R.layout.item_viewpager_discovery_discuss, null);
        rv = (PullLoadMoreRecyclerView) view_discuss.findViewById(R.id.rv);
        //EditText et1 = (EditText) view_discuss.findViewById(R.id.map_discuss_et);
        //ImageButton btn1 = (ImageButton) view_discuss.findViewById(R.id.map_discuss_btn);
        //btn1.setOnClickListener(this);

        rv.setLinearLayout();
//        setList();
        //getdata();

        rv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                setRefresh();
                getData();
            }

            @Override
            public void onLoadMore() {
                mCount = mCount + 1;
                getData();
            }
        });
        map_pageview = new ArrayList<>();
        //添加想要切换的界面
        map_pageview.add(view_info);
        map_pageview.add(view_discuss);
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return map_pageview.size();
            }

            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(map_pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(map_pageview.get(arg1));
                return map_pageview.get(arg1);
            }
        };
        //绑定适配器
        map_viewPager.setAdapter(mPagerAdapter);
        //设置viewPager的初始界面为第一个界面
        map_viewPager.setCurrentItem(0);
        //添加切换界面的监听器
        map_viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        if (aMap == null) {
            aMap = mMapView.getMap();
            //缩放按钮设置
            UiSettings uiSettings = aMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);
        }
        setUpMap();
    }

    private void setRefresh() {
        discussAdapter.removeAllDataList();
        mCount = 1;
    }
    private void getdata(){
        new OnekeySharedDiscussionBizImpl().findMessageAllDiscussions(c,10,0,new OnekeySharedDiscussionBiz.FindDiscussionsLisenter(){
            @Override
            public void onStart() {
            }
            @Override
            public void onSuccess(List<OnekeySharedDisc> discussions) {
                discussAdapter = new DiscoveryDiscussAdapter(discussions, getActivity());
                rv.setAdapter(discussAdapter);
//                discussAdapter.setOnItemClickListener(new DiscoveryDiscussAdapter.OnRecycleViewtemClickListener() {
//                    @Override
//                    public void onItemClick(View view, String datas) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setPositiveButton("回复", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                // TODO Auto-generated method stub
//                            }
//                        });
//                        builder.setNegativeButton("分享", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                // TODO Auto-generated method stub
//                            }
//                        });
//                        AlertDialog dialog = builder.create();
//                        dialog.setCancelable(false);
//                        dialog.show();
//                    }
//                });
                Toast.makeText(getActivity(), "查询成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(BmobException e) {

            }
        });
    }
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 把更新ui的代码创建在Runnable中，然后在需要更新ui时，把这个Runnable对象传给Activity.runOnUiThread(Runnable)。
                 Runnable对像就能在ui程序中被调用。如果当前线程是UI线程，那么行动是立即执行。如果当前线程不是UI线程,操作是发布到事件队列的UI线程。
                 */
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getdata();
                        discussAdapter.notifyDataSetChanged();
                        rv.setPullLoadMoreCompleted();
                    }
                });
            }
        }).start();
    }
    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView.onResume();
        StatusBarUtils.setWindowStatusBarColor(getActivity(),
                ContextCompat.getColor(getApplicationContext(), R.color.bg_blue_deep),false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位
    }

    private void updatePosition(Location location) {
        //获取经纬度
        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
        //创建一个设置经纬度的CameraUpdate
        CameraUpdate cu = CameraUpdateFactory.changeLatLng(pos);
        //更新地图的显示区域
        aMap.moveCamera(cu);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        //清除所有的Marker等覆盖物
        //aMap.clear();
        //marker(pos,-1);
        getOtherShared(pos);
    }

    private void getOtherShared(LatLng pos){
        new OnekeySharedMessageBizImpl().findOthersSharedByLatLng(pos.latitude, pos.longitude, new OnekeySharedMessageBiz.FindSharedDoingLisenter() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List<OnekeySharedMessage> objects) {
                if(objects != null){
                    for(OnekeySharedMessage message : objects){
                        marker(message.getLat(),message.getLng(),message.getResultMark(),message);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "其他分享不存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(BmobException e) {
                BmobError.showErrorMessage(getApplicationContext(),e);
            }
        });
    }
    private void marker(final double a , final double b , int num,OnekeySharedMessage message) {
        LatLng pos = new LatLng(a,b);

        //创建一个MarkerOptions对象
        MarkerOptions markOptions = new MarkerOptions();
        markOptions.position(pos);
        markOptions.draggable(true);
        markOptions.visible(true);
        markOptions.title(pos + "");
        if(num>=90){
            markOptions.icon(
                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getActivity().getResources(),
                                    R.drawable.mark_best)));
        }else if(num>=80&&num<90){
            markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getActivity().getResources(),
                            R.drawable.mark_good)));
        }else if(num>=60&&num<80){
            markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getActivity().getResources(),
                            R.drawable.mark_average)));
        }else if(num<60&&num>=0){
            markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getActivity().getResources(),
                            R.drawable.mark_poor)));
        }else{
            markOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        //点击监听事件
        AMap.OnMarkerClickListener clickListener = new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                OnekeySharedMessage message = (OnekeySharedMessage) arg0.getObject();
                Log.e("7",message.getObjectId());
                c = message.getObjectId();
                getdata();
                tv_map_info.setText("地址：" + message.getProvince() + " "+message.getCity()+""+message.getDistrict()+""+message.getStreet()+""
                +"\n"+"评分："+message.getResultMark()+"\n"+"建议："+message.getSuggest()+"");

                return false;
            }
        };

        aMap.setOnMarkerClickListener(clickListener);

        //添加MarkerOptions（实际上是添加Marker）,拖拽监听事件
        AMap.OnMarkerDragListener dragListener = new AMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "kjsadgh", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub

            }
        };
        aMap.setOnMarkerDragListener(dragListener);
        aMap.addMarker(markOptions).setObject(message);
    }

    //执行评论等具体操作
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_info_btn:
                String a = map_info_et.getText().toString().trim();
                OnekeySharedDisc discussion = new OnekeySharedDisc(messageId,userId);
                discussion.setMessage(a);
                //设置messageId
                discussion.setSharedMessageId(c);
                //获得用户名
                discussion.setUsername(BmobUser.getCurrentUser(User.class).getUsername());
                //获得用户id
                discussion.setUserId(BmobUser.getCurrentUser(User.class).getObjectId());
                discussion.setUsersex(true);
                discussion.setUserImgUrl(BmobUser.getCurrentUser(User.class).getImgUrl());


                new OnekeySharedDiscussionBizImpl().reportDiscussion(discussion, new OnekeySharedDiscussionBiz.DiscussionDoingLisenter() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity(), "Send Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(BmobException e) {
                        BmobError.showErrorMessage(getApplicationContext(),e);
                    }
                });
                break;
        }
    }

    /**
     * 信息部分
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}