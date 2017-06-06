package com.olife.o_life.entity;

import cn.bmob.v3.BmobObject;

/**
 * 检测记录类 一旦检测完成 就会把结果总结 上传到后台服务器 只支持基本的格式bmob 记录也要加地理位置
 * 然后分享的时候的地理位置的获取就是获取这个地理位置里面的信息
 * 如果地理位置为空 则在分享的时候再测一次 把分享的时候的地理位置给填上
 * Created by chenfuhai on 2016/12/16 0016.
 */

public class OnekeyResultRecord {

    private int id;
    private Integer resultMark;//结果总分数
    private Integer ben;//苯详细评分
    private Integer pm2_5;//PM2.5详细评分
    private String suggest;//科学建议
    //private String time;//不需要 有updatetimeAT这个属性


    private Double lng;
    private Double lat;
    private String  province;
    private String  district;
    private String  city;
    private String  street;
    private String  StreetNum;


    private String userId;
    private String driverId;//设备编号

    private String  time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNum() {
        return StreetNum;
    }

    public void setStreetNum(String streetNum) {
        StreetNum = streetNum;
    }


    public OnekeyResultRecord(Integer resultMark, String userId) {
        this.resultMark = resultMark;
        this.userId = userId;
    }

    public Integer getResultMark() {
        return resultMark;
    }

    public void setResultMark(Integer resultMark) {
        this.resultMark = resultMark;
    }

    public Integer getBen() {
        return ben;
    }

    public void setBen(Integer ben) {
        this.ben = ben;
    }

    public Integer getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(Integer pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
