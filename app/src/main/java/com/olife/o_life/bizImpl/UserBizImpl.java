package com.olife.o_life.bizImpl;

import android.graphics.Bitmap;
import android.provider.Settings;

import com.olife.o_life.biz.UserBiz;
import com.olife.o_life.entity.User;
import com.olife.o_life.util.GsonGetter;
import com.olife.o_life.util.HttpUtils;
import com.olife.o_life.util.NetConfig;
import com.olife.o_life.util.SDcardTools;
import com.olife.o_life.util.UserPwdChange;
import com.olife.o_life.util.UserUtils;

import java.io.File;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by chenfuhai on 2016/12/15 0015.
 */

public class UserBizImpl implements UserBiz {


    @Override
    public void updateUserHead(final User user, Bitmap imgmap, final UserDoingLisenter lisenter) {
        lisenter.onStart();

        //保存头像文件
        SDcardTools.savaUserHead(imgmap, user.getUsername());
        //上传头像文件
//        final BmobFile headFile = new BmobFile(SDcardTools.getUserHeadForFile(user.getUsername()));
//        headFile.uploadblock(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    //头像上传成功 得到URL 填入用户类中 并更新用户类
//                    User mUser = new User();
//                    mUser.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
//                    mUser.setImgUrl(headFile.getFileUrl());
//                    updateUser(mUser, new UserDoingLisenter() {
//                        @Override
//                        public void onStart() {
//                        }
//
//                        @Override
//                        public void onSuccess() {
//                            lisenter.onSuccess();
//                        }
//
//                        @Override
//                        public void onFailed(BmobException e) {
//                            lisenter.onFailed(e);
//                        }
//                    });
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
        File headFile = SDcardTools.getUserHeadForFile(user.getUsername());
        HttpUtils.getInstance().postwithFile(NetConfig.UploadUserImgAction, "img", headFile, null, new HttpUtils.SuccessListener() {
            @Override
            public void onSuccessResponse(String result) {
                //头像上传成功 得到URL 填入用户类中 并更新用户类
                User user = UserUtils.currentUser();
                user.setImgUrl(result);
                HttpUtils.getInstance().postwithJSON(NetConfig.UpdateUserAction,
                        GsonGetter.getInstance().getGson().toJson(user),
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
        }, new HttpUtils.FailedListener() {
            @Override
            public void onFialed(int connectCode) {
                lisenter.onFailed(connectCode);
            }
        });

    }


    @Override
    public void updateUser(User user, final UserDoingLisenter lisenter) {
        lisenter.onStart();

//        user.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });

        HttpUtils.getInstance().postwithJSON(NetConfig.UpdateUserAction,
                GsonGetter.getInstance().getGson().toJson(user), new HttpUtils.SuccessListener() {
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
    public void changePwd(String oldPwd, String newPwd, final UserDoingLisenter lisenter) {
        lisenter.onStart();
//        BmobUser.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//
//        });

        UserPwdChange userPwdChange = new UserPwdChange();
        userPwdChange.setId(UserUtils.currentUser().getId());
        userPwdChange.setOldPwd(oldPwd);
        userPwdChange.setNewPwd(newPwd);

        HttpUtils.getInstance().postwithJSON(NetConfig.ChangeUserPwdAction,
                GsonGetter.getInstance().getGson().toJson(userPwdChange),
                new HttpUtils.SuccessListener() {
                    @Override
                    public void onSuccessResponse(String result) {
                        lisenter.onSuccess();;
                    }
                }, new HttpUtils.FailedListener() {
                    @Override
                    public void onFialed(int connectCode) {
                        lisenter.onFailed(connectCode);
                    }
                });
    }

    @Override
    public void verifySmsCode(final String phone, String code, final UserDoingLisenter lisenter) {
        //暂时不实现
//        lisenter.onStart();
//        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    User user = new User();
//                    user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
//                    user.setMobilePhoneNumber(phone);
//                    user.setMobilePhoneNumberVerified(true);
//                    updateUser(user, new UserDoingLisenter() {
//                        @Override
//                        public void onStart() {
//
//                        }
//
//                        @Override
//                        public void onSuccess() {
//                            lisenter.onSuccess();
//                        }
//
//                        @Override
//                        public void onFailed(BmobException e) {
//                            lisenter.onFailed(e);
//                        }
//                    });
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
    }

    @Override
    public void requestSMSCode(String phone, final UserDoingLisenter lisenter) {
        //暂时不实现
//        lisenter.onStart();
//        BmobSMS.requestSMSCode(phone, "fuhai", new QueryListener<Integer>() {
//            @Override
//            public void done(Integer integer, BmobException e) {
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//        });
    }

    @Override
    public void loginBySMSCode(String phone, String code, final UserDoingLisenter lisenter) {
        //暂时不实现
//        lisenter.onStart();
//        BmobUser.loginBySMSCode(phone, code, new LogInListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//
//                if (e == null) {
//                    lisenter.onSuccess();
//                } else {
//                    lisenter.onFailed(e);
//                }
//            }
//
//
//        });
    }
}
