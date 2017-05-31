package com.olife.o_life.bizImpl;

import com.olife.o_life.biz.GoodsBiz;
import com.olife.o_life.entity.Goods;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wuguofei on 2016/12/26.
 */

public class GoodsBizImpl implements GoodsBiz {
    @Override
    public void FindAllGoods(int limit, int skit, final FindGoodsListener listener) {
        listener.onStart();
        BmobQuery<Goods> query = new BmobQuery<>();
        query.setSkip(skit);
        query.setLimit(limit);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null) {
                    listener.onSuccess(list);
                } else {
                    listener.onFailed(e);
                }
            }
        });
    }
}
