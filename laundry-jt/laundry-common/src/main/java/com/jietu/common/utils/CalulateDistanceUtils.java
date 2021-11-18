package com.jietu.common.utils;

import java.math.BigDecimal;

/**
 * 计算距离工具类
 * @author: 印修河
 * @date: 2019/1/30 16:31
 */
public class CalulateDistanceUtils {

    /** 地球半径,单位米 */
    private static final double EARTH_RADIUS = 6378138;

    /**
     * 计算两个坐标的距离
     * @param latitude1 第一个经度
     * @param longitude1 第一个纬度
     * @param latitude2 第一个经度
     * @param longitude2 第一个纬度
     * @return
     */
    public static Long CalulateTwoLanLonDistance(BigDecimal latitude1, BigDecimal longitude1, BigDecimal latitude2, BigDecimal longitude2){
        return Math.round(
            EARTH_RADIUS * 2 * Math.asin(
                Math.sqrt(
                    Math.pow(
                            Math.sin((rad(latitude1) - rad(latitude2)) / 2), 2)
                            + Math.cos(rad(latitude1)) * Math.cos(rad(latitude2))
                            * Math.pow(Math.sin((rad(longitude1) - rad(longitude2)) / 2
                    ), 2)
                )
            )
        );
    }

    private static double rad(BigDecimal d) {
        return d.doubleValue() * Math.PI / 180;
    }

    /**
     * 计算两个坐标的距离
     * @param latitude1 第一个经度
     * @param longitude1 第一个纬度
     * @param latitude2 第一个经度
     * @param longitude2 第一个纬度
     * @return
     */
    public static Long CalulateTwoLanLonDistance(double latitude1, double longitude1, double latitude2, double longitude2){
        return Math.round(
            EARTH_RADIUS * 2 * Math.asin(
                Math.sqrt(
                    Math.pow(
                            Math.sin((rad(latitude1) - rad(latitude2)) / 2), 2)
                            + Math.cos(rad(latitude1)) * Math.cos(rad(latitude2))
                            * Math.pow(Math.sin((rad(longitude1) - rad(longitude2)) / 2
                    ), 2)
                )
            )
        );
    }

    private static double rad(double d) {
        return d * Math.PI / 180;
    }

}
