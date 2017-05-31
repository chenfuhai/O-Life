package com.olife.o_life.entity;

import android.graphics.Bitmap;

/**
 * 一键检测的结果
 * Created by chenfuhai on 2016/12/6 0006.
 */

public class OnekeyResultLocal {
    /**
     * 提示文字
     */
    private String tipString;
    /**
     * 类别图片资源
     */
    private Bitmap tagIcon;

    /**
     * 是否检测完成
     */
    private boolean checkIsOk;

    public OnekeyResultLocal(String tipString, Bitmap tagIcon, boolean checkIsOk) {
        this.tipString = tipString;
        this.tagIcon = tagIcon;
        this.checkIsOk = checkIsOk;

    }

    public String getTipString() {
        return tipString;
    }

    public void setTipString(String tipString) {
        this.tipString = tipString;
    }

    public Bitmap getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(Bitmap tagIcon) {
        this.tagIcon = tagIcon;
    }

    public boolean isCheckIsOk() {
        return checkIsOk;
    }

    public void setCheckIsOk(boolean checkIsOk) {
        this.checkIsOk = checkIsOk;
    }
}
