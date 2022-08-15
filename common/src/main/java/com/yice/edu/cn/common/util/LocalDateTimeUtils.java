package com.yice.edu.cn.common.util;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.DatePaper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * jdk8的新的时间api很好用
 * LocalDateTime工具类
 * @see java.time.LocalDateTime
 * @author dengfengfeng
 *
 */
public class LocalDateTimeUtils {

	public static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static final DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static final DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
	
	public static final DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");
	
	/**
	 * 得到当前的LocalDateTime
	 * @return
	 */
	public static LocalDateTime now() {
		return LocalDateTime.now();
	}
	
	/**
	 * 得到当前LocalDateTime对应的字符串 yyyy-MM-dd HH:mm:ss 格式 字符串
	 * @return yyyy-MM-dd HH:mm:ss 格式 字符串
	 */
	public static String nowString() {
		return LocalDateTime.now().format(yyyyMMddHHmmss);
	}
	
	/**
	 * 得到当前日期LocalDate
	 * @return
	 */
	public static LocalDate nowDate() {
		return LocalDate.now();
	}
	
	/**
	 * 得到当前日期LocalDate yyyy-MM-dd格式
	 * @return yyyy-MM-dd格式 字符串
	 */
	public static String nowDateString() {
		return LocalDate.now().format(yyyyMMdd);
	}
	
	
	/**
	 * 将yyyy-MM-dd的字符串转成 LocalDate
	 * @param date
	 * @return LocalDate
	 */
	public static LocalDate getLocalDateByDateString(String date) {
		return LocalDate.parse(date, yyyyMMdd);
	}

	/**
	 * 将LocalDate 转成yyyy-MM的字符串
	 * @param localDate
	 * @return yyyy-MM的字符串
	 */
	public static String getMonthStringByLocalDate(LocalDate localDate) {
		return localDate.format(yyyyMM);
	}
	
	/**
	 * 将LocalDate 转成yyyy-MM-dd的字符串
	 * @param localDate
	 * @return yyyy-MM-dd的字符串
	 */
	public static String getDateStringByLocalDate(LocalDate localDate) {
		return localDate.format(yyyyMMdd);
	}
	
	/**
	 * 得到这个月第一天的日期
	 * @param localDate
	 * @return LocalDate
	 */
	public static LocalDate getThisMonthFirstDateByLocalDate(LocalDate localDate) {
		return LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
	}
	
	/**
	 * 得到下个月的第一天的日期
	 * @param localDate
	 * @return LocalDate
	 */
	public static LocalDate getNextMonthFirstDateByLocalDate(LocalDate localDate) {
		return getThisMonthFirstDateByLocalDate(localDate).plusMonths(1);
	}
	
	/**
	 * 得到这个月的最后一天的日期 
	 * @param localDate
	 * @return LocalDate
	 */
	public static LocalDate getThisMonthLastDateByLocalDate(LocalDate localDate) {
		return getNextMonthFirstDateByLocalDate(localDate).minusDays(1);
	}
	
	
	/**
	 * 根据 DayOfWeek 得到星期几 的中文
	 * @param dayOfWeek
	 * @return
	 */
	public static String getChineseByDayOfWeek(DayOfWeek dayOfWeek) {
		String chinese = null;
		switch (dayOfWeek) {
		case MONDAY:
			chinese = "星期一";
			break;
		case TUESDAY:
			chinese = "星期二";	
			break;
		case WEDNESDAY:
			chinese = "星期三";
			break;
		case THURSDAY:
			chinese = "星期四";
			break;
		case FRIDAY:
			chinese = "星期五";
			break;
		case SATURDAY:
			chinese = "星期六";
			break;
		case SUNDAY:
			chinese = "星期日";
			break;

		default:
			break;
		}
		return chinese;
	}

	/**
	 * 获取上一年
	 * @return
	 */
	public static Integer getNextYear(){
		return LocalDateTime.now().minusYears(1).getYear();
	}

	/**
	 * 获取前一年
	 */
	public static Integer getTheYearBeforeLast(){
		return LocalDateTimeUtils.now().minusYears(2).getYear();
	}

	/**
	 *获取N天前的时间
	 */
	public static String getNextDays(int i){
		return LocalDateTimeUtils.now().minusDays(i).format(yyyyMMddHHmmss);
	}

	/**
	 *获取N天前的时间
	 */
	public static String getNextYearMonthDay(int i){
		return LocalDateTimeUtils.now().minusDays(i).format(yyyyMMdd);
	}

	/**
	 * 获取第N天前的天数
	 */
	public static long getUpNday(int i){
		return LocalDateTimeUtils.now().minusDays(i).getDayOfMonth();
	}

	/**
	 * 获取第N天前的月数
	 */
	public static long getUpNMonth(int i){
		return LocalDateTimeUtils.now().minusDays(i).getMonthValue();
	}

	/**
	 * 获取第N天前的年数
	 */
	public static long getUpNYear(int i){
		return LocalDateTimeUtils.now().minusDays(i).getYear();
	}

	/**
	 * 传递需要查询的天数数量
	 * @param i
	 * @return 返回衰减后的月份和日期数
	 */
	public static List<DatePaper> getMonthDay(int i){
		List<DatePaper> datePapers = new ArrayList<>();
		for(int j =1;j<=i;j++){
			DatePaper datePaper = new DatePaper();
			datePaper.setDay(LocalDateTimeUtils.getUpNday(j));
			datePaper.setMonth(LocalDateTimeUtils.getUpNMonth(j));
			datePaper.setYearMonthDay(LocalDateTimeUtils.getNextYearMonthDay(j));
			datePapers.add(datePaper);
		}
		return datePapers;
	}

	/**
	 * 获取本月到N个月之前的时间数组
	 * @param i
	 * @return
	 */
	public static List<String> getYearMonthDay(int i){
		List<String> strings = new ArrayList<>();
		for(int j=0;j<i;j++){
			String time = LocalDateTimeUtils.now().minusMonths(j).format(yyyyMMdd);
			strings.add(time);
		}
		return strings;
	}

	/**
	 *获取今天到N天之前的时间数组
	 */
	public static List<String> getMonthDays(int i){
		List<String> l = new ArrayList<>();
		for(int j=0;j<i;j++){
			String time = LocalDateTimeUtils.now().minusDays(j).format(yyyyMMdd);
			l.add(time);
		}
		return l;
	}
}
