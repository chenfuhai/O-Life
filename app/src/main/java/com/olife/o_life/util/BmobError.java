//package com.olife.o_life.util;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import cn.bmob.v3.exception.BmobException;
//
///**
// * 错误类 方便展示Bmob的错误
// * Created by chenfuhai on 2016/10/14 0014.
// */
//
//public class BmobError {
//
//    /**
//     * 显示错误信息
//     * @param context app的context
//     * @param e Bmob的错误类BmobException
//     */
//    public static void showErrorMessage(Context context, BmobException e) {
//        switch (e.getErrorCode()) {
//            case 101:
//                Toast.makeText(context, "用户名或密码不正确", Toast.LENGTH_LONG).show();
//                break;
//            case 108:
//                Toast.makeText(context, "必须提供用户名和密码", Toast.LENGTH_LONG).show();
//                break;
//
//            case 202:
//                Toast.makeText(context, "用户名已存在", Toast.LENGTH_LONG).show();
//                break;
//            case 203:
//                Toast.makeText(context, "邮箱已存在", Toast.LENGTH_LONG).show();
//                break;
//            case 205:
//                Toast.makeText(context, "此邮箱或账号未注册", Toast.LENGTH_LONG).show();
//                break;
//            case 207:
//                Toast.makeText(context, "验证码错误", Toast.LENGTH_LONG).show();
//                break;
//            case 209:
//                Toast.makeText(context, "手机号码已存在", Toast.LENGTH_LONG).show();
//                break;
//
//            case 301:
//                Toast.makeText(context, "邮箱或手机号格式不正确"+e.getMessage(), Toast.LENGTH_LONG).show();
//                break;
//
//            case 9014:
//                Toast.makeText(context, "第三方授权失败", Toast.LENGTH_LONG).show();
//                break;
//            case 9015:
//                Toast.makeText(context, "其他错误"+e.getMessage(), Toast.LENGTH_LONG).show();
//                break;
//            case 9016:
//                Toast.makeText(context, "无网络连接请，检查您的网络", Toast.LENGTH_LONG).show();
//                break;
//            case 9010:
//                Toast.makeText(context, "网络超时", Toast.LENGTH_LONG).show();
//                break;
//            case 9019:
//                Toast.makeText(context, "格式不正确", Toast.LENGTH_LONG).show();
//                break;
//
//            case 10010:
//                Toast.makeText(context, "该手机号发送短信达到限制,一天内最多10条，一小时内5条", Toast.LENGTH_LONG).show();
//                break;
//
//            default:
//                Toast.makeText(context, "未知错误" + e.getErrorCode() + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//}
