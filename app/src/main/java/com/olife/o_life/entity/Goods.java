package com.olife.o_life.entity;

/**
 * Created by wuguofei on 2016/12/26.
 */

public class Goods {
    private int id;
    private String iconurl;
    private String name;
    private String desc;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIconUrl() {
        return iconurl;
    }

    public void setIconUrl(String iconurl) {
        this.iconurl = iconurl;
    }
}
