package com.yice.edu.cn.common.util.weekDayUtil;

import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoTime {
    /**
     * 判断一个时间是否在一个时间段之间
     * 日期格式：xxxx-wxTempConfig-wxTempConfig 2018-05-26
     * @param m 为目标时间
     * @param st 为起始时间
     * @param et 为结束时间
     * @return if st<=m<=et true else false
     */
    public static boolean getInDate(String m,String st,String et) {

        boolean a =false;

        //获得年月日
        try {
            int ym=Integer.parseInt(m.substring(0, 4));
            int mm=Integer.parseInt(m.substring(5, 7));
            int dm=Integer.parseInt(m.substring(8, 10));

            int yst=Integer.parseInt(st.substring(0, 4));
            int mst=Integer.parseInt(st.substring(5, 7));
            int dst=Integer.parseInt(st.substring(8, 10));

            int yet=Integer.parseInt(et.substring(0, 4));
            int met=Integer.parseInt(et.substring(5, 7));
            int det=Integer.parseInt(et.substring(8, 10));

            //第一步比较年份
            if(ym>yet||ym<yst) {
                a=false;
            }else {
                if(mm>met||mm<mst) {
                    a=false;
                }else {
                    if(dm>det||dm<dst) {
                        a=false;
                    }else {
                        a=true;
                    }
                }
            }
        }catch (Exception e) {
            //处理异常  异常来源：格式不符合xxxx-wxTempConfig-wxTempConfig 或者全为数字
            System.out.println("数据格式未正确");
        }
        return a;

    }

    /**
     *
     * @param date 日期
     * @param time 时分
     * @param during 时间间隔
     * @return 允许最早的时间
     */
    public static String getPuchLater(String date,String time,String during) {
        //允许打卡的最早时间
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str= "";
        try {
            cal.setTime(sdf.parse(date+" "+time));
            int minute = Integer.parseInt(during);
            cal.add(Calendar.MINUTE,-minute);
            str = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 维护数据字典周几和id
     * @param str
     * @return
     * @throws ParseException
     */
    public static String[] getDutyDayAndId(String str){
        String[] arry = new String[2];
        String str1 = "";
        String str2 = "";
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"星期日");
        map.put(2,"星期一");
        map.put(3,"星期二");
        map.put(4,"星期三");
        map.put(5,"星期四");
        map.put(6,"星期五");
        map.put(7,"星期六");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int i=0;
        try {
            i = DateUtil.dayOfWeek(sdf.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(Integer key:map.keySet()){
            if(i==key) {
                str1 = map.get(key).toString();

            }
        }
        Map<String,String> map1 = new HashMap<>();
        map1.put("星期日","406");
        map1.put("星期一","400");
        map1.put("星期二","401");
        map1.put("星期三","402");
        map1.put("星期四","403");
        map1.put("星期五","404");
        map1.put("星期六","405");
        for(String key:map1.keySet()){
            if(str1.equals(key)) {
                str2 = map1.get(key).toString();
            }
        }
        arry[0] = str1;arry[1] = str2;
        return arry;
    }

    public static void main(String[] args) {
        String data ="2018-05-21";
        String st ="2018-05-20";
        String et ="2018-05-30";
        boolean inDate = getInDate(data,st,et);
        System.out.println(inDate);
    }
}
