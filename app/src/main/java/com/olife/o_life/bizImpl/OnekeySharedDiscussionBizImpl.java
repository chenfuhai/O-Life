package com.olife.o_life.bizImpl;

import com.olife.o_life.biz.OnekeySharedDiscussionBiz;
import com.olife.o_life.entity.OnekeySharedDisc;
import com.olife.o_life.util.Delete;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.Query;

import java.util.ArrayList;

/**
 * 评论的业务实现类
 * Created by chenfuhai on 2016/12/26 0026.
 */

public class OnekeySharedDiscussionBizImpl implements OnekeySharedDiscussionBiz {
    @Override
    public void reportDiscussion(OnekeySharedDisc discussion, final DiscussionDoingLisenter lisenter) {
        lisenter.onStart();
//        discussion.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
        HttpUtils.getInstance().postwithJSON(NetConfig.reportDiscussionAction,
                GsonGetter.getInstance().getGson().toJson(discussion),
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
    public void findMessageAllDiscussions(String messageId, int limit, int skip, final FindDiscussionsLisenter lisenter) {
        lisenter.onStart();
//        BmobQuery<OnekeySharedDisc> query = new BmobQuery<>();
//        query.addWhereEqualTo("sharedMessageId", messageId);
//        query.setLimit(limit);
//        query.setSkip(skip);
//        query.order("-updatedAt");
//        query.findObjects(new FindListener<OnekeySharedDisc>() {
//            @Override
//            public void done(List<OnekeySharedDisc> list, BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess(list);
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
        Query query = new Query();
        query.setWhereEqualTo(new String[]{"sharedMessageId",messageId});
        query.setLimit(limit);
        query.setSkip(skip);
        query.setOrder("-id");
        HttpUtils.getInstance().postwithJSON(NetConfig.findMessageAllDiscussions,
                GsonGetter.getInstance().getGson().toJson(query),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        ArrayList<OnekeySharedDisc> list = GsonGetter.getInstance().getGson().fromJson(result,ArrayList.class);
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
    public void findUserAllDiscussions(String userId, int limit, int skip,final FindDiscussionsLisenter lisenter) {
        lisenter.onStart();
//        BmobQuery<OnekeySharedDisc> query = new BmobQuery<>();
//        query.addWhereEqualTo("userId", userId);
//        query.setLimit(limit);
//        query.setSkip(skip);
//        query.order("-updatedAt");
//        query.findObjects(new FindListener<OnekeySharedDisc>() {
//            @Override
//            public void done(List<OnekeySharedDisc> list, BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess(list);
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
        Query query = new Query();
        query.setWhereEqualTo(new String[]{"userId",userId});
        query.setLimit(limit);
        query.setSkip(skip);
        query.setOrder("-id");
        HttpUtils.getInstance().postwithJSON(NetConfig.findUserAllDiscussionsAction,
                GsonGetter.getInstance().getGson().toJson(query),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        ArrayList<OnekeySharedDisc> list = GsonGetter.getInstance().getGson().fromJson(result,ArrayList.class);
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
    public void deleteDiscussion(String discussionId, final DiscussionDoingLisenter lisenter) {
        lisenter.onStart();
//        BmobObject discussion = new BmobObject();
//        discussion.setObjectId(discussionId);
//        discussion.delete(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
        Delete delete = new Delete();
        delete.setTableName("onekeySharedDiscussion");
        delete.setWhereEquelTo(new String[]{"discussionId",discussionId});
        HttpUtils.getInstance().postwithJSON(NetConfig.deleteDiscussionAction,
                GsonGetter.getInstance().getGson().toJson(delete),
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
    public void deleteUserAllDiscussion(String userId, final DiscussionDoingLisenter lisenter) {
        lisenter.onStart();
//        BmobQuery<OnekeySharedDisc> query = new BmobQuery<>();
//        query.addWhereEqualTo("userId", userId);
//        query.findObjects(new FindListener<OnekeySharedDisc>() {
//            @Override
//            public void done(List<OnekeySharedDisc> list, BmobException e) {
//                if (e == null) {
//                    //先查询到用户所有的评论
//                    // 然后把查询到的评论的ID取出来 全部删除
//                    List<BmobObject> discussions = new ArrayList<BmobObject>();
//                    for (OnekeySharedDisc temp :list){
//                        BmobObject object = new BmobObject();
//                        object.setObjectId(temp.getObjectId());
//                        discussions.add(object);
//                    }
//
//                    //全部删除
//                    new BmobBatch().deleteBatch(discussions).doBatch(new QueryListListener<BatchResult>() {
//                        @Override
//                        public void done(List<BatchResult> list, BmobException e) {
//                            if (e == null){
//                                for (int i = 0;i<list.size();i++){
//                                    BmobException ex = list.get(i).getError();
//                                    if (ex == null){
//                                        Log.i("fuhai", "done:批量删除 第"+i+"个删除成功");
//                                    }else {
//                                        Log.i("fuhai", "done:批量删除 第"+i+"个删除失败"+ex.getErrorCode()+ex.getMessage());
//                                    }
//                                }
//                                lisenter.onSuccess();
//                            }else {
//                                lisenter.onFailed(e);
//                            }
//                        }
//                    });
//
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });

        Delete delete = new Delete();
        delete.setTableName("onekeySharedDiscussion");
        delete.setWhereEquelTo(new String[]{"userId",userId});

        HttpUtils.getInstance().postwithJSON(NetConfig.deleteDiscussionAction,
                GsonGetter.getInstance().getGson().toJson(delete),
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
}
