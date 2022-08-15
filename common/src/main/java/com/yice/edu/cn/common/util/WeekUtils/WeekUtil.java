package com.yice.edu.cn.common.util.WeekUtils;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class WeekUtil {
	public static void main(String[] args) {
		//DateUtil.format(date, "yyyy-MM-dd");
		System.out.println(getBeginDayOfWeek());
		System.out.println(getEndDayOfWeek());
	}
	@SuppressWarnings("unused")
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}
	
	// 获取某个日期的开始时间
		public static Timestamp getDayStartTime(Date d) {
			Calendar calendar = Calendar.getInstance();
			if (null != d)
				calendar.setTime(d);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return new Timestamp(calendar.getTimeInMillis());
		}
	
		// 获取本周的结束时间
		public static Date getEndDayOfWeek() {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getBeginDayOfWeek());
			cal.add(Calendar.DAY_OF_WEEK, 6);
			Date weekEndSta = cal.getTime();
			return getDayEndTime(weekEndSta);
	   }
		// 获取某个日期的结束时间
		public static Timestamp getDayEndTime(Date d) {
			Calendar calendar = Calendar.getInstance();
			if (null != d)
				calendar.setTime(d);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return new Timestamp(calendar.getTimeInMillis());
		}
}
