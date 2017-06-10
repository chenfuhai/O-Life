package com.olife.o_life.bizImpl;

import android.util.Log;

import com.amap.api.maps.offlinemap.City;
import com.google.gson.reflect.TypeToken;
import com.olife.o_life.biz.OnekeyResultBiz;
import com.olife.o_life.entity.OnekeyResultRecord;
import com.olife.o_life.entity.OnekeySharedMessage;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.Query;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by chenfuhai on 2016/12/16 0016.
 */

public class OnekeyResultBizImpl implements OnekeyResultBiz {
    @Override
    public void saveOnekeyResult(OnekeyResultRecord onekeyResultRecord, final OnekeyDoingLisenter lisenter) {
        lisenter.onStart();

//        onekeyResultRecord.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    lisenter.onSuccess();
//                }else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });

        HttpUtils.getInstance().postwithJSON(NetConfig.SaveonekeyResultRecordAction,
                GsonGetter.getInstance().getGson().toJson(onekeyResultRecord),
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
    public void findOnkeyResultByUserId(String userId, int count, int skip, final FindDoingLisenter lisenter) {
        lisenter.onStart();
//        BmobQuery<OnekeyResultRecord> query = new BmobQuery<>();
//        query.addWhereEqualTo("userId",userId);
//        query.setLimit(count);
//        query.setSkip(skip);
//        query.order("-updatedAt");
//        query.findObjects(new FindListener<OnekeyResultRecord>() {
//            @Override
//            public void done(List<OnekeyResultRecord> list, BmobException e) {
//
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
        query.setSkip(skip);
        query.setOrder("-id");
        HttpUtils.getInstance().postwithJSON(NetConfig.findOnkeyResultByUserIdAction,
                GsonGetter.getInstance().getGson().toJson(query),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        Type type = new TypeToken<ArrayList<OnekeyResultRecord>>(){}.getType();
                        ArrayList<OnekeyResultRecord> list = GsonGetter.getInstance().getGson().fromJson(result, type);
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
    public void findLastOnkeyResultByUserId(String userId, final FindLastDoingLisenter lisenter) {
        lisenter.onStart();
//        BmobQuery<OnekeyResultRecord> query = new BmobQuery<>();
//        query.addWhereEqualTo("userId",userId);
//        query.setLimit(1);
//        query.order("-updatedAt");//时间按照降序 只查询一个
//        query.findObjects(new FindListener<OnekeyResultRecord>() {
//            @Override
//            public void done(List<OnekeyResultRecord> list, BmobException e) {
//                if (e == null){
//
//                    if (list.size()!=0){
//                        lisenter.onSuccess(list.get(0));
//                    }else {
//                        lisenter.onSuccess(null);
//                    }
//                }else {
//                    lisenter.onSuccess(null);//这里 如果Bmob上面的表示空的 那么会出现错误
//                    // 也可以认为 表中没有数据 那么就没有得到所需要的记录
//                    lisenter.onFailed(e);
//                }
//            }
//        });


        Query query = new Query();
        query.setOrder("-id");
        query.setLimit(1);
        query.setWhereEqualTo(new String[]{"userid",userId});

        Log.i("fuhai", "com.olife.o_life.bizImpl>>OnekeyResultBizImpl>>findLastOnkeyResultByUserId: "+query.toString());
        Log.i("fuhai", "com.olife.o_life.bizImpl>>OnekeyResultBizImpl>>findLastOnkeyResultByUserId: "+GsonGetter.getInstance().getGson().toJson(query));
        HttpUtils.getInstance().postwithJSON(NetConfig.findLastOnkeyResultByUserIdAction,
                GsonGetter.getInstance().getGson().toJson(query),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        Log.i("fuhai", "com.olife.o_life.bizImpl>>OnekeyResultBizImpl>>onSuccessResponse: "+result);
                        if (result==null || result.equals("")){
                            lisenter.onFailed(500);
                        }else{
                            Type type = new TypeToken<ArrayList<OnekeyResultRecord>>() {}.getType();
                            ArrayList<OnekeyResultRecord> list = GsonGetter.getInstance().getGson().fromJson(result, type);
                            Log.i("fuhai", "com.olife.o_life.bizImpl>>OnekeyResultBizImpl>>onSuccessResponse: "+list.get(0));
                            if (list!=null && list.size() != 0) {
                                lisenter.onSuccess(list.get(0));
                            } else {
                                lisenter.onSuccess(null);
                            }
                        }
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        lisenter.onSuccess(null);//这里 如果Bmob上面的表示空的 那么会出现错误
                        // 也可以认为 表中没有数据 那么就没有得到所需要的记录
                        lisenter.onFailed(connectCode);
                    }
                });
    }
}
