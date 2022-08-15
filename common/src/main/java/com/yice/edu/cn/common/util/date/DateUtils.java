package com.yice.edu.cn.common.util.date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @date ：Created in 2019/4/19 13:19
 * @description：根据"yyyy-MM-dd"格式的时间模糊查询时，自动变成"yyyy-MM-dd 00:00:00"或者"yyyy-MM-dd 23:59:59"
 * @modified By：
 * @version:
 */
@Slf4j
public final class DateUtils {

    private DateUtils(){}

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat formatterHH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Date getNow() {
        String now = DateUtil.today();
        log.warn("----------"+now);
        try {
            return formatter.parse(now);
        } catch (ParseException e) {
            log.error("", e);
        }
        return null;
    }

    public static Date getNowDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusHours(13);
        return new Date(localDateTime.getYear()-1900, localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
    }

    /**
     *@Description 根据当前时间推算出当天最早或最晚时间
     *@Param [currentDate 当前时间, mode --1 当前最早时间 2---当天最晚时间]
     *@Return java.lang.String
     *@Author ly
     *@Date 2019/4/19
     *@Time 13:30
     */
    public static String getOriginalDateTime(String currentDate,int mode) {
        if (StringUtils.isNoneBlank(currentDate)) {
            if (1 == mode) {
                return DateUtil.format(DateUtil.beginOfDay(DateUtil.parseDate(currentDate)), "yyyy-MM-dd HH:mm:ss");
            } else {
                return DateUtil.format(DateUtil.endOfDay(DateUtil.parseDate(currentDate)), "yyyy-MM-dd HH:mm:ss");
            }
        }
        return currentDate;
    }


    /**
     * 判断时间集合中是否有时间段冲突
     * List："2019-06-14 15:00:02~2019-06-14 16:00:02"，
     *       "2019-06-14 16:00:02~2019-06-14 17:00:02"
     * @param list
     * @return
     * @throws ParseException
     */

    public static Boolean DateRangeJudge(List<String> list)  {
        Collections.sort(list);
        boolean flag = false;
        for(int i=0; i<list.size(); i++){
            if(i>0){
                String[] itime = list.get(i).split("~");
                for(int j=0; j<list.size(); j++){
                    if(j==i || j>i){
                        continue;
                    }

                    String[] jtime = list.get(j).split("~");
                    int compare = 0;
                    try {
                        compare = formatterHH.parse(itime[0]).compareTo(formatterHH.parse(jtime[1]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(compare<=0){
                        flag = true;
                        break;
                    }
                }
            }

            if(flag){
                break;
            }
        }

        return flag;

    }


    /**
     *
     *  算出一个时间mou之前 type  false 或者 之后的mou时间  true
     *
     * */
    public static String getDateByMinute(String beginDate,Integer mou,boolean type){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(formatterHH.parse(beginDate));
            if(type){
                cal.add(Calendar.MINUTE,mou);
            }else{
                cal.add(Calendar.MINUTE,-mou);
            }
            Date time = cal.getTime();
            return formatterHH.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 1   表示 date1 时间 大于 date2 时间
     * -1  表示 date1 时间 小于 date2 时间
     * 0   表示相等
     * 500 表示异常
     * */
    public static Integer compareDate(String date1, String date2){
        try {
            Date dt1 = formatterHH.parse(date1);
            Date dt2 = formatterHH.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return 500;
        }

    }
    /**
     * 格式化系统时间 yyyy-MM-dd HH:mm:ss 风格
     * */
    public static String Nowss(){
       return formatterHH.format(new Date());
    }


    /**
     * 转换当前上课时间
     * @param str
     * @return
     */
    public static String getClassTime(String str){
        if(StringUtils.isNotBlank(str)){
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = formatter.format(new Date());
            String[] strArr = currentDate.split(" ");
            String resultTime = strArr[0] + " " + str + ":00";
            return resultTime;
        }
        return "";
    }

    /**
     * 根据传入的时间 查询当前的课程时间
     * @param date
     * @param time
     * @return
     */
    public static String getCurrentClassTime(String date,String time){
        if(StringUtils.isNotBlank(date) && StringUtils.isNotBlank(time)){
            String pattern = "^\\d{2}:\\d{2}$";
            boolean match = Pattern.matches(pattern, time);
            if(!match){
                time="0"+time;
            }
            String  currentDate = DateUtil.format(DateUtil.parse(date, "yyyy-MM-dd"),"yyyy-MM-dd");
            String resultTime=currentDate+" "+time+":00";
            return resultTime;
        }
        return "";
    }


    /**
     * 计算当月第一天
     * @param date
     * @return
     */
    public static  String getMonthOfDate(String date){
        if(StringUtils.isNotBlank(date)){
            return DateUtil.format(DateUtil.beginOfMonth(DateUtil.parse(date, "yyyy-MM")),"yyyy-MM-dd HH:mm:ss");
        }
        return "";
    }

    public static String getEndofDay(String date){
        if(StringUtils.isNotBlank(date)){
            return DateUtil.format(DateUtil.endOfMonth(DateUtil.parse(date,"yyyy-MM")),"yyyy-MM-dd HH:mm:ss");
        }
        return "";
    }

    /**
     * 判断sourceDate是否小于targetDate
     * @param sourceDate 源时间
     * @param targetDate 目标时间
     * @param  format1 sourceDate时间格式
     * @param format2 targetDate时间格式
     * @return
     */
    public static boolean isBefore(String sourceDate,String targetDate,String format1,String format2){
        return  DateUtil.parse(sourceDate, format1).isBefore(DateUtil.parse(targetDate,format2));
    }

    /**
     * 判断当前sourceDate 是否大于targetDate
     * @param sourceDate
     * @param targetDate
     * @param  format1 sourceDate时间格式
     * @param format2 targetDate时间格式
     * @return
     */
    public static boolean isAfter(String sourceDate,String targetDate,String format1,String format2){
        return  DateUtil.parse(sourceDate, format1).isAfter(DateUtil.parse(targetDate,format2));
    }

    /**
     *判断sourceDate 是否大于等于targetDate
     * @param sourceDate
     * @param targetDate
     * @param  format1 sourceDate时间格式
     * @param format2 targetDate时间格式
     * @return
     */
    public static boolean isAfterOrEquals(String sourceDate,String targetDate,String format1,String format2){
        return DateUtil.parse(sourceDate,format1).isAfterOrEquals(DateUtil.parse(targetDate,format2));
    }

    /**
     * 获取本周的时间段范围
     * @return
     */
    public static String[] getThisWeekRange(){
        DateTime endTime = DateTime.now();
        DateTime beginTime = DateUtil.beginOfWeek(endTime);
        return new String[]{DateUtil.format(beginTime, Constant.DATE_FORMATTER),DateUtil.format(endTime,Constant.DATE_FORMATTER)};
    }

    /**
     * 获取上周的时间段
     * @return
     */
    public static String[] getLastWeekRange(){
        DateTime beginTime = DateUtil.beginOfWeek(DateUtil.lastWeek());
        DateTime endTime=DateUtil.endOfWeek(DateUtil.lastWeek());
        return new String[]{DateUtil.format(beginTime, Constant.DATE_FORMATTER),DateUtil.format(endTime,Constant.DATE_FORMATTER)};
    }

    /***
     * 获取上上周时间范围
     * @return
     */
    public static String[] getLastLastWeekRange(){
        DateTime lastTime = DateUtil.beginOfWeek(DateUtil.lastWeek());
        DateTime dateTime = DateUtil.offsetDay(lastTime, -7);
        DateTime beginTime = DateUtil.beginOfWeek(dateTime);
        DateTime endTime = DateUtil.endOfWeek(dateTime);
        return new String[]{DateUtil.format(beginTime, Constant.DATE_FORMATTER),DateUtil.format(endTime,Constant.DATE_FORMATTER)};
    }

    /**
     * 获取本月时间段
     * @return
     */
    public static String[] getThisMonth(){
        DateTime endTime = DateTime.now();
        DateTime beginTime = DateUtil.beginOfMonth(endTime);
        return new String[]{DateUtil.format(beginTime, Constant.DATE_FORMATTER),DateUtil.format(endTime,Constant.DATE_FORMATTER)};
    }

    /**
     * 获取上月时间段
     * @return
     */
    public static String[] getLastMonth(){
        DateTime beginTime = DateUtil.beginOfMonth(DateUtil.lastMonth());
        DateTime endTime = DateUtil.endOfMonth(beginTime);
        return new String[]{DateUtil.format(beginTime, Constant.DATE_FORMATTER),DateUtil.format(endTime,Constant.DATE_FORMATTER)};
    }

    public static void main(String[] args) {
        String beginTime = getOriginalDateTime(DateUtil.now(),1);
        System.out.println(beginTime);
    }
}
