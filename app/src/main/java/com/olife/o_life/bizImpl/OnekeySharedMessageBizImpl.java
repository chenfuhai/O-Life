package com.olife.o_life.bizImpl;

import com.amap.api.maps.model.animation.AlphaAnimation;
import com.olife.o_life.biz.OnekeySharedMessageBiz;
import com.olife.o_life.entity.OnekeyResultRecord;
import com.olife.o_life.entity.OnekeySharedMessage;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.LagLngUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.Query;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.amap.api.mapcore2d.p.c;
import static com.amap.api.mapcore2d.p.l;

/**
 * Created by chenfuhai on 2016/12/16 0016.
 */

public class OnekeySharedMessageBizImpl implements OnekeySharedMessageBiz {
    @Override
    public void shareUserResult(OnekeySharedMessage sharedMessage, final DiscoverDoingLisenter lisenter) {
        lisenter.onStart();
//        sharedMessage.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    lisenter.onSuccess();
//                }else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });

        HttpUtils.getInstance().postwithJSON(NetConfig.shareUserResultAction,
                GsonGetter.getInstance().getGson().toJson(sharedMessage),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        lisenter.onSuccess();
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        lisenter.onFailed(connectCode);
                    }
                });
    }

    @Override
    public void findOthersSharedByLatLng(double lat, double lng, final FindSharedDoingLisenter lisenter) {
        lisenter.onStart();

        //距离中心1500米
        long radius = 1500;

        LagLngUtils.Result result = LagLngUtils.getSquare(lat,lng,radius);

//        BmobQuery<OnekeySharedMessage> eq1 = new BmobQuery<>();
//        eq1.addWhereLessThanOrEqualTo("lat", result.getMaxLat());//纬度<=最大纬度
//
//        BmobQuery<OnekeySharedMessage> eq2 = new BmobQuery<>();
//        eq2.addWhereGreaterThanOrEqualTo("lat", result.getMinLat());//纬度>=最小纬度
//
//        BmobQuery<OnekeySharedMessage> eq3 = new BmobQuery<>();
//        eq3.addWhereLessThanOrEqualTo("lng", result.getMaxLng());//经度<=最大经度
//
//        BmobQuery<OnekeySharedMessage> eq4 = new BmobQuery<>();
//        eq4.addWhereGreaterThanOrEqualTo("lng", result.getMinLng());//经度>=最小经度
//
//        List<BmobQuery<OnekeySharedMessage>> queries = new ArrayList<>();
//        queries.add(eq1);
//        queries.add(eq2);
//        queries.add(eq3);
//        queries.add(eq4);
//
//        BmobQuery<OnekeySharedMessage> query = new BmobQuery<>();
//        query.and(queries);//与查询
//
//        query.findObjects(new FindListener<OnekeySharedMessage>() {
//            @Override
//            public void done(List<OnekeySharedMessage> list, BmobException e) {
//                if (e ==null){
//                    lisenter.onSuccess(list);
//                }else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
        Query query = new Query();
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(new String[]{"lat",result.getMaxLat()+""});
        arrayList.add(new String[]{"lat",result.getMinLat()+""});
        query.setWhereGreaterThanOrEqualTo(arrayList);
        ArrayList<String[]> arrayList2 = new ArrayList<>();
        arrayList2.add(new String[]{"lng",result.getMaxLng()+""});
        arrayList2.add(new String[]{"lng",result.getMinLng()+""});
        query.setWhereLessThanOrEqualTo(arrayList2);

        HttpUtils.getInstance().postwithJSON(NetConfig.findOthersSharedByLatLngAction,
                GsonGetter.getInstance().getGson().toJson(query), new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        ArrayList<OnekeySharedMessage> list = GsonGetter.getInstance().getGson().fromJson(result,ArrayList.class);
                        lisenter.onSuccess(list);
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        lisenter.onFailed(connectCode);
                    }
                });

    }

    @Override
    public void findOnkeySharedByUserId(String userId, int count, final FindSharedDoingLisenter lisenter) {
        lisenter.onStart();
//        BmobQuery<OnekeySharedMessage> query = new BmobQuery<>();
//        query.addWhereEqualTo("userId",userId);
//        query.setLimit(count);
//        query.findObjects(new FindListener<OnekeySharedMessage>() {
//            @Override
//            public void done(List<OnekeySharedMessage> list, BmobException e) {
//                if (e == null){
//                    lisenter.onSuccess(list);
//                }else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });

        Query query = new Query();
        query.setWhereEqualTo(new String[]{"userId",userId});
        query.setLimit(count);
        HttpUtils.getInstance().postwithJSON(NetConfig.findOnkeySharedByUserIdAction,
                GsonGetter.getInstance().getGson().toJson(query), new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        ArrayList<OnekeySharedMessage> list = GsonGetter.getInstance().getGson().fromJson(result,ArrayList.class);
                        lisenter.onSuccess(list);
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        lisenter.onFailed(connectCode);
                    }
                });

    }
}
