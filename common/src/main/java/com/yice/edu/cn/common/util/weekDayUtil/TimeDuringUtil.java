package com.yice.edu.cn.common.util.weekDayUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 处理一段时间重叠
 */
public class TimeDuringUtil {
    public static void main(String[] args) throws Exception {
//		List<String> yearMonth = getMonthBetween("2018-01-02","2019-02-05");
//		for (String string : yearMonth) {
//			System.out.println(string);
//		}
//		String str = getYearMonthSb("2018-01-02","2019-02-05");
//		System.out.println(str);

        //开始时间begintTime，截止时间endTime
        String begintTime = "2018-06-06";
        String endTime =  "2018-06-16";
        List<String> findDaysStr = findDaysStr(begintTime,endTime);
        for (String string : findDaysStr) {
            System.out.println(string);
        }
    }
    /**
     *
     * @param minDate 最小时间  2015-01
     * @param maxDate 最大时间 2015-10
     * @return日期集合 格式为 年-月字符串
     * @throws Exception
     */
    public static String getYearMonthSb(String minDate, String maxDate) throws Exception {
        List<String> result = getMonthBetween(minDate,maxDate);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<result.size();i++) {
            sb.append(result.get(i));
            if(i<result.size()-1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    /**
     *
     * @param minDate 最小时间  2015-01
     * @param maxDate 最大时间 2015-10
     * @return 日期集合 格式为 年-月
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     *
     * @param minDate 最小时间  2015-01-12
     * @param maxDate 最大时间 2015-01-23
     * @return日期集合 格式为 年-月字符串
     * @throws Exception
     */
    public static String findDaysStrSb(String minDate, String maxDate) throws Exception {
        List<String> result = findDaysStr(minDate,maxDate);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<result.size();i++) {
            sb.append(result.get(i));
            if(i<result.size()-1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    /**
     * JAVA获取某段时间内每天的日期（String类型，格式为："2018-06-16"）
     * @param begintTime
     * @param endTime
     * @return
     */
    public static List<String> findDaysStr(String begintTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(begintTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //存放每一天日期String对象的daysStrList
        List<String> daysStrList = new ArrayList<String>();
        //放入开始的那一天日期String
        daysStrList.add(sdf.format(dBegin));

        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);

        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);

        // 判断循环此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，给定的日历字段增加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }

        return daysStrList;
    }



}
