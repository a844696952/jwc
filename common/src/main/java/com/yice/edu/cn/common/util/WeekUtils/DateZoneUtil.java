package com.yice.edu.cn.common.util.WeekUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;  
import java.util.Calendar;  
import java.util.Date;  
import java.util.List;  
  
  
public class DateZoneUtil {
      
    private List<Date> dayList = new ArrayList<Date>();

    public Date yesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }
    public Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    public long countDays(Date startDate, Date endDate) {
        startDate=  yesterday(startDate);
        long startValue = startDate.getTime();  
          
        long endValue = endDate.getTime();  
          
        if (startValue > endValue) {  
            long temp = endValue;  
            endValue = startValue;  
            startValue = temp;  
        }  
          
        // 计算天数差  
        long result = (endValue - startValue + 1) / 1000 / 60 / 60 / 24;
          
        Calendar cal = Calendar.getInstance();  
      
        cal.setTime(startDate);
          
        // 计算期间的每一天  
        for (int index = 0; index < result; index++) {
            cal.add(Calendar.DATE, 1);
            dayList.add(cal.getTime());  
        }  
          
        return result;  
    }  
      
      
    public List<Date> getDayList() {  
        return dayList;  
    }  

  
    public static void main(String args[]) {  
        Calendar cal1 = Calendar.getInstance();  
          
        // 2010/1/9  
        cal1.set(Calendar.YEAR, 2010);  
        cal1.set(Calendar.MONTH, 0);  
        cal1.set(Calendar.DATE, 9);  
          
        // 2010/1/29  
        Calendar cal2 = Calendar.getInstance();  
        cal2.set(Calendar.YEAR, 2010);  
        cal2.set(Calendar.MONTH, 0);  
        cal2.set(Calendar.DATE, 29);  
          
        Date startDate = cal1.getTime();  
        Date endDate = cal2.getTime();
        System.out.println(startDate);
        DateZoneUtil dateCal = new DateZoneUtil();
          
        System.out.println(dateCal.countDays(startDate,  endDate));  
          
        List<Date> list = dateCal.getDayList();  
          
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EEE");  
          
        for (Date date : list) {  
            System.out.println(dateFormat.format(date));  
        }  
          
    }  
}  