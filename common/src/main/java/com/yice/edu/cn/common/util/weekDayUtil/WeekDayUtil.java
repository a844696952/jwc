package com.yice.edu.cn.common.util.weekDayUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取某一时间段特定星期几的日期
 */
public class WeekDayUtil {
	public static void main(String[] args) {
        String[] dates = getDates("2019-03-05", "2019-03-06","星期日");
        System.out.println("datates:"+dates.length);
        for (String string : dates) {
			System.out.println(string);
		}
	}


	/**
	 * 获取某一时间段特定星期几的日期
	 * @param dateFrom 开始时间
	 * @param dateEnd 结束时间
	 * @param weekDays 星期
	 * @return 返回时间数组
	 */
    public static String[] getDates(String dateFrom, String dateEnd, String weekDays) {
        long time = 1l;
        long perDayMilSec = 24 * 60 * 60 * 1000;
        List<String> dateList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //需要查询的星期系数
        String strWeekNumber = weekForNum(weekDays);
        try {
			dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
			while (true) {
					time = sdf.parse(dateFrom).getTime();
					time = time + perDayMilSec;
					Date date = new Date(time);
					dateFrom = sdf.format(date);
					if (dateFrom.compareTo(dateEnd) <= 0) {
						//查询的某一时间的星期系数
						Integer weekDay = dayForWeek(date);                    
						//判断当期日期的星期系数是否是需要查询的
						if (strWeekNumber.indexOf(weekDay.toString())!=-1) {
							System.out.println(dateFrom);
							dateList.add(dateFrom);
						}
					} else {
						break;
					}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        String[] dateArray = new String[dateList.size()];
        dateList.toArray(dateArray);
        return dateArray;
    }
    //等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer dayForWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 得到对应星期的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
     * @param weekDays 星期格式  星期一|星期二
     */
    public static String weekForNum(String weekDays){
    	//返回结果为组合的星期系数
    	String weekNumber = "";
    	//解析传入的星期
    	if(weekDays.indexOf("|")!=-1){//多个星期数
    		String []strWeeks = weekDays.split("\\|");
    		for(int i=0;i<strWeeks.length;i++){
    			weekNumber = weekNumber + "" + getWeekNum(strWeeks[i]).toString();
    		}
    	}else{//一个星期数
    		weekNumber = getWeekNum(weekDays).toString();
    	}
    	
    	return weekNumber;
    	
    }
    
    //将星期转换为对应的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer getWeekNum(String strWeek){
    	Integer number = 1;//默认为星期日
    	if("星期日".equals(strWeek)){
    		number = 1;
    	}else if("星期一".equals(strWeek)){
    		number = 2;
    	}else if("星期二".equals(strWeek)){
    		number = 3;
    	}else if("星期三".equals(strWeek)){
    		number = 4;
    	}else if("星期四".equals(strWeek)){
    		number = 5;
    	}else if("星期五".equals(strWeek)){
    		number = 6;
    	}else if("星期六".equals(strWeek)){
    		number = 7;
    	}
    	return number;
    }

	/**
	 * 查找给定年月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最小天数
		int firstDay = cal.getMinimum(Calendar.DATE);
		//设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH,firstDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String data = sdf.format(cal.getTime());
		return data;
	}

	/**
	 * 查找给定年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String data =  sdf.format(cal.getTime());
		return data;
	}

	//转化为周几，默认周日
	public static  String getWeek(int number){
		if(number==1){
			return "周一";
		}else if(number==2){
			return "周二";
		}else if(number==3){
			return "周三";
		}else if(number==4){
			return "周四";
		}else if(number==5){
			return "周五";
		}else if(number==6){
			return "周六";
		}else{
			return "周日";
		}
	}

	/**
	 * 返回周对应的数值
	 * @param weekName
	 * @return
	 */
	public static Integer returnWeekNumber(String weekName){
		Map<String,Integer> map = new HashMap<>();
		map.put("周一",1);
		map.put("周二",2);
		map.put("周三",3);
		map.put("周四",4);
		map.put("周五",5);
		map.put("周六",6);
		map.put("周日",7);
		return map.get(weekName);
	}

	/**
	 * 当前节数
	 * 每日节数
	 * @param index
	 * @param count
	 * @return
	 */
	public static String returnWeek(Integer index,Integer count){
		if(0<index&&index<=count){
			return "周一";
		}else if(count<index && index<=2*count){
			return "周二";
		}else if(2*count<index && index<=3*count){
			return "周三";
		}else if(3*count<index && index<=4*count){
			return "周四";
		}else if(4*count<index && index<=5*count){
			return "周五";
		}else if(5*count<index && index<=6*count){
			return "周六";
		}else{
			return "周日";
		}
	}
}