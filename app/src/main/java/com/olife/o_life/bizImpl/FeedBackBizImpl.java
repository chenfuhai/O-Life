package com.olife.o_life.bizImpl;

import com.google.gson.reflect.TypeToken;
import com.olife.o_life.biz.FeedBackBiz;
import com.olife.o_life.entity.Feedback;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.Query;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by chenfuhai on 2016/12/26 0026.
 */

public class FeedBackBizImpl implements FeedBackBiz {
    @Override
    public void submitFeedBack(Feedback feedback, final FeedbackDoingLisenter lisenter) {
        lisenter.onStart();

        HttpUtils.getInstance().postwithJSON(NetConfig.SaveFeedBackAction,
                GsonGetter.getInstance().getGson().toJson(feedback),
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
                }
        );
//        feedback.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
    }

    @Override
    public void findAllFeedBack(int limit, int skip, final FindFeedbacksLisenter lisenter) {
        lisenter.onStart();
        Query query = new Query();

        query.setLimit(limit);
        query.setSkip(skip);
        HttpUtils.getInstance().postwithJSON(NetConfig.FindAllFeedBackAction,
                GsonGetter.getInstance().getGson().toJson(query),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        Type type = new TypeToken<ArrayList<Feedback>>(){}.getType();
                        ArrayList<Feedback> list = GsonGetter.getInstance().getGson().fromJson(result, type);
                        lisenter.onSuccess(list);
                    }
                },
                new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        lisenter.onFailed(connectCode);
                    }
                });
//        BmobQuery<Feedback> query = new BmobQuery<>();
//
//        query.setLimit(limit);
//        query.setSkip(skip);
//        query.findObjects(new FindListener<Feedback>() {
//            @Override
//            public void done(List<Feedback> list, BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess(list);
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
    }
}
