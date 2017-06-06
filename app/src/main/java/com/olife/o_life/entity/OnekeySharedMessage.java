package com.olife.o_life.entity;

import cn.bmob.v3.BmobObject;

/**
 * 一键检测结果分享类 包含用户的位置信息 检测的结果信息 用户的信息等
 * Created by chenfuhai on 2016/12/16 0016.
 */

public class OnekeySharedMessage {

    private int id;
    private Integer  resultMark;//结果总分数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Integer ben;//苯详细评分
    private Integer  pm2_5;//PM2.5详细评分
    private String  suggest;//科学建议

    private Double lng;
    private Double lat;
    private String  province;
    private String  district;
    private String  city;
    private String  street;
    private String  StreetNum;

    private String  userId;
    private String  username;
    private String userImgUrl;
    private String  userphone;
    private String  useremail;
    private String  usersex;
    private String  userage;

    private String driverId;//设备编号

    public OnekeySharedMessage(Integer resultMark, String userId, Double lat, Double lng) {
        this.resultMark = resultMark;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUserage() {
        return userage;
    }

    public void setUserage(String userage) {
        this.userage = userage;
    }


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
