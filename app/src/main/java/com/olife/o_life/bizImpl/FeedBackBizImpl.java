package com.olife.o_life.bizImpl;

import com.olife.o_life.biz.FeedBackBiz;
import com.olife.o_life.entity.Feedback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenfuhai on 2016/12/26 0026.
 */

public class FeedBackBizImpl implements FeedBackBiz {
    @Override
    public void submitFeedBack(Feedback feedback, final FeedbackDoingLisenter lisenter) {
        lisenter.onStart();
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    lisenter.onSuccess();
                } else {
                    lisenter.onFailed(e);
                }
            }
        });
    }

    @Override
    public void findAllFeedBack(int limit, int skip, final FindFeedbacksLisenter lisenter) {
        lisenter.onStart();
        BmobQuery<Feedback> query = new BmobQuery<>();

        query.setLimit(limit);
        query.setSkip(skip);
        query.findObjects(new FindListener<Feedback>() {
            @Override
            public void done(List<Feedback> list, BmobException e) {
                if (e == null) {
                    lisenter.onSuccess(list);
                } else {
                    lisenter.onFailed(e);
                }
            }
        });
    }
}
