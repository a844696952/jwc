package com.yice.edu.cn.common.util.cron;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.util.weekDayUtil.WeekDayUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*动态任务使用中*/


public class CronUtil {

    private static final String TRANS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String TRANS_CRON_FORMAT_ONCE = "ss mm HH dd MM ? yyyy";

    private static final String TRANS_CRON_FORMAT_DAY = "ss mm HH dd/ * ? *";

    private static final String TRANS_CRON_FORMAT_WEEK = "ss mm HH ? * -- *";

    private static final String TRANS_CRON_FORMAT_MONTH = "ss mm HH dd MM/1 ? *";

    public static void main(String[] args) throws Exception {
        String result = getCron("day", "2000-01-01 12:11:00");
//       String result = getCron("MON", "2018-08-11 12:11:00");
        // String result = getCronByOnce("2017-01-01 12:12:12");
//       String result = getCron("month", "2019-01-01 12:00:00");
        // String result = getCronToDate("12 12 12 01 01/1 ? 2018");
  /*     int i = result.indexOf("?");
      result = result.substring(0,i+1);

        System.out.println(result);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 10);
        System.out.println(df.format(nowTime.getTime()));
        nowTime.add(Calendar.MINUTE, -20);
        System.out.println(df.format(nowTime.getTime()));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "2019-02-30 10:10:10";
        Date date=simpleDateFormat.parse(time);
        System.out.println(date);
        nowTime.setTime(date);
        nowTime.add(Calendar.MINUTE, 10);
        System.out.println(df.format(nowTime.getTime()));

        System.out.println( "90101749018".split("")[0]);
        System.out.println(DateUtil.today());
*/
        String nowTime1 = DateUtil.now().substring(11, 15);
        System.out.println(nowTime1+"0");

        System.out.println(DateUtil.yesterday().offset(DateField.WEEK_OF_YEAR,-1));
        System.out.println(DateUtil.lastWeek().toString().substring(0,10)+" 00:00:00");
        System.out.println(DateUtil.date().offset(DateField.MONTH,-1).toString().substring(0,10));
        System.out.println(DateUtil.yesterday().offset(DateField.MONTH,-2).toString().substring(0,10));
        System.out.println(DateUtil.yesterday().offset(DateField.MONTH,-1).toString().substring(0,10));

       /* System.out.println(DateUtil.parse(nowTime1+":00"));
        String s = DateUtil.yesterday().toString();

        System.out.println( s.substring(0,11));
        String lastDayOfMonth = WeekDayUtil.getFirstDayOfMonth(2019, 5);
        String today = DateUtil.today();
        System.out.println(today.substring(5,7));
        System.out.println(lastDayOfMonth.substring(5,7));*/


    }

    /**
     * 生成只执行一次的cron
     */
    public static String getCronByOnce(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
        Date parse = format.parse(dateStr);
        return fmtDateToStr(parse, TRANS_CRON_FORMAT_ONCE);
    }

    /**
     * 生成每月或每周或每天执行的cron
     */
    public static String getCron(String period, String startDateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TRANS_DATE_FORMAT);
        Date startDate = format.parse(startDateStr);
        StringBuffer cronStringBuffer = new StringBuffer();
        if ("month".equals(period)) {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_MONTH).trim();
            cronStringBuffer.append(startDateCron);
        } else if ("day".equals(period)) {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_DAY).trim();
            // StringBuffer stringBuffer = new StringBuffer(str);
            // stringBuffer.insert(stringBuffer.lastIndexOf("/") + 1, period);
            // cron = stringBuffer.toString().trim();
            // createPeriod(arrStart, arrEnd, 4);
            cronStringBuffer.append(startDateCron).insert(cronStringBuffer.lastIndexOf("/") + 1, "1");
        } else {
            String startDateCron = fmtDateToStr(startDate, TRANS_CRON_FORMAT_WEEK).trim();
            cronStringBuffer.append(startDateCron.replaceAll("--", period));
        }
        return cronStringBuffer.toString();
    }


    public static String getCronToDate(String cron) {
        String format = null;
        StringBuffer stringBuffer = new StringBuffer(cron);
        int lastIndexOf = stringBuffer.lastIndexOf("/");
        stringBuffer.deleteCharAt(lastIndexOf);
        stringBuffer.deleteCharAt(lastIndexOf);
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date = sdf.parse(stringBuffer.toString());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format = sdf.format(date);
        } catch (Exception e) {
            return null;
        }
        return format;
    }

    /**
     * 格式转换 日期-字符串 
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}