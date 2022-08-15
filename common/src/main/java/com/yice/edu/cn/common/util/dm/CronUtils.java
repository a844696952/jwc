package com.yice.edu.cn.common.util.dm;

public class CronUtils {
    /**
     * 将时间转换为cron表达式
     * @param time 时间（hh:mm）
     * @param week 周（周一到周日）
     */
    public static String timeToCron(String time, String week) {
         //将时间字符串分割成String数组
/*        String cron ="";
        String [] timeStr = time.split(":");
        if(timeStr[1].equals("00")){
             cron = "0 0 "+timeStr[0]+" ? * "+week;
        }else{
             cron = "0 "+timeStr[1]+" "+timeStr[0]+" ? * "+week;
        }*/
        return "*/5 * * * * ?";
    }
}
