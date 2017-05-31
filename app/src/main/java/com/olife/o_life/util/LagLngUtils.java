package com.olife.o_life.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 输入一个经纬度 计算离这个经纬度一定距离的经纬度区域
 * Created by chenfuhai on 2016/12/16 0016.
 */

public  class LagLngUtils {

    public  static class Result{
        private Double minLat;
        private Double minLng;
        private Double maxLat;
        private Double maxLng;

        public Result( Map<String, Double> result) {
            this.minLat = result.get("minLat");
            this.minLng = result.get("minLng");
            this.maxLat = result.get("maxLat");
            this.maxLng = result.get("maxLng");
        }

        public Double getMinLat() {
            return minLat;
        }

        public void setMinLat(Double minLat) {
            this.minLat = minLat;
        }

        public Double getMinLng() {
            return minLng;
        }

        public void setMinLng(Double minLng) {
            this.minLng = minLng;
        }

        public Double getMaxLat() {
            return maxLat;
        }

        public void setMaxLat(Double maxLat) {
            this.maxLat = maxLat;
        }

        public Double getMaxLng() {
            return maxLng;
        }

        public void setMaxLng(Double maxLng) {
            this.maxLng = maxLng;
        }

        @Override
        public String toString() {
            return "maxLat"+getMaxLat()+"\n"+"maxLng"+getMaxLng()+"\n"
                    +"minLat"+getMinLat()+"\n"+"minLng"+getMinLng();
        }
    }

    /**
     * 给定一个经纬度 和距离 计算出距离这个位置多少米的经纬度范围
     * @param lat 给定的lat纬度
     * @param lng 给定的lng经度
     * @param raidus 距离半径 使用M作为单位 整数
     * @return 返回结果类
     */
    public static Result getSquare( double lat, double lng, long raidus){
        Double latitude = lat;
        Double longitude = lng;
        long raidusMile = raidus;

        // 赤道周长24901英里 1609是转换成米的系数
        Double degree = (24901 * 1609) / 360.0;


        Map<String, Double> result = new HashMap<>();

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;

        result.put("minLat", minLat);
        result.put("minLng", minLng);
        result.put("maxLat", maxLat);
        result.put("maxLng", maxLng);

        return new Result(result);
    }

}
