package com.yice.edu.cn.common.util.map;

public class MapUtil {
    private static double rad(double d) {
        return d * Math.PI / 180.00; // 角度转换成弧度
    }

    /*
     * 根据经纬度计算两点之间的距离（单位米）
     */
    public static double algorithm(double longitude1, double latitude1, double longitude2, double latitude2) {

        // 纬度
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        // 两点纬度之差
        double a = Lat1 - Lat2;
        // 经度之差
        double b = rad(longitude1) - rad(longitude2);

        // 计算两点距离的公式
        double s = 2 * Math.asin(Math
                .sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));

        // 弧长乘地球半径（半径为米）
        s = s * 6378137.0;
        // 精确距离的数值
        s = Math.round(s * 10000d) / 10000d;
/*        // 四舍五入 保留一位小数
        DecimalFormat df = new DecimalFormat("#.0");*/
        //向上取整
        s = Math.ceil(s);
        return s;
    }

    public static void main(String[] args) {
        System.out.println(algorithm(114.336338,30.545209,114.339363,30.544211));
    }
}
