package com.olife.o_life.bizImpl;

import com.google.gson.reflect.TypeToken;
import com.olife.o_life.biz.GoodsBiz;
import com.olife.o_life.entity.Feedback;
import com.olife.o_life.entity.Goods;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.Query;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by wuguofei on 2016/12/26.
 */

public class GoodsBizImpl implements GoodsBiz {
    @Override
    public void FindAllGoods(int limit, int skit, final FindGoodsListener listener) {
        listener.onStart();

        Query query = new Query();
        query.setLimit(limit);
        query.setSkip(skit);

        HttpUtils.getInstance().postwithJSON(NetConfig.FindAllGoodsAction,
                GsonGetter.getInstance().getGson().toJson(query),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        Type type = new TypeToken<ArrayList<Goods>>(){}.getType();
                        ArrayList<Goods> list = GsonGetter.getInstance().getGson().fromJson(result,type);
                        listener.onSuccess(list);
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        listener.onFailed(connectCode);
                    }
                });

//        BmobQuery<Goods> query = new BmobQuery<>();
//        query.setSkip(skit);
//        query.setLimit(limit);
//        query.findObjects(new FindListener<Goods>() {
//            @Override
//            public void done(List<Goods> list, BmobException e) {
//                if (e == null) {
//                    listener.onSuccess(list);
//                } else {
//                    listener.onFailed(e);
//                }
//            }
//        });
    }
}
