package com.olife.o_life.bizImpl;

import android.util.Log;

import com.olife.o_life.biz.OnekeyResultBiz;
import com.olife.o_life.entity.OnekeyResultRecord;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenfuhai on 2016/12/16 0016.
 */

public class OnekeyResultBizImpl implements OnekeyResultBiz {
    @Override
    public void saveOnekeyResult(OnekeyResultRecord onekeyResultRecord, final OnekeyDoingLisenter lisenter) {
        lisenter.onStart();
        onekeyResultRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    lisenter.onSuccess();
                }else {
                    lisenter.onFailed(e);
                }
            }
        });
    }

    @Override
    public void findOnkeyResultByUserId(String userId, int count, int skip,final FindDoingLisenter lisenter) {
        lisenter.onStart();
        BmobQuery<OnekeyResultRecord> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(count);
        query.setSkip(skip);
        query.order("-updatedAt");
        query.findObjects(new FindListener<OnekeyResultRecord>() {
            @Override
            public void done(List<OnekeyResultRecord> list, BmobException e) {

                if (e == null){
                    lisenter.onSuccess(list);
                }else {
                    lisenter.onFailed(e);
                }
            }
        });
    }

    @Override
    public void findLastOnkeyResultByUserId(String userId, final FindLastDoingLisenter lisenter) {
        lisenter.onStart();
        BmobQuery<OnekeyResultRecord> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",userId);
        query.setLimit(1);
        query.order("-updatedAt");//时间按照降序 只查询一个
        query.findObjects(new FindListener<OnekeyResultRecord>() {
            @Override
            public void done(List<OnekeyResultRecord> list, BmobException e) {
                if (e == null){

                    if (list.size()!=0){
                        lisenter.onSuccess(list.get(0));
                    }else {
                        lisenter.onSuccess(null);
                    }
                }else {
                    lisenter.onSuccess(null);//这里 如果Bmob上面的表示空的 那么会出现错误
                    // 也可以认为 表中没有数据 那么就没有得到所需要的记录
                    lisenter.onFailed(e);
                }
            }
        });
    }
}
