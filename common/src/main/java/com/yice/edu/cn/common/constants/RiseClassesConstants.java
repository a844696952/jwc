package com.yice.edu.cn.common.constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RiseClassesConstants {

	public static final Map<String, Map<String, Integer>> gradeMap = new HashMap<String, Map<String, Integer>>();

	static {

		// 高中
		Map<String, Integer> heightSchoolMap = new HashMap<String, Integer>();
		heightSchoolMap.put("高一", 1);
		heightSchoolMap.put("高二", 2);
		heightSchoolMap.put("高三", 3);
		gradeMap.put("高中", heightSchoolMap);

		// 初中
		Map<String, Integer> middleSchoolMap = new HashMap<String, Integer>();
		middleSchoolMap.put("初一", 1);
		middleSchoolMap.put("初二", 2);
		middleSchoolMap.put("初三", 3);
		gradeMap.put("初中", middleSchoolMap);
		
		// 初中2
		Map<String, Integer> middle2SchoolMap = new HashMap<String, Integer>();
		middle2SchoolMap.put("七年级", 1);
		middle2SchoolMap.put("八年级", 2);
		middle2SchoolMap.put("九年级", 3);
		gradeMap.put("其他", middle2SchoolMap);

		// 小学
		Map<String, Integer> smallSchoolMap = new HashMap<String, Integer>();
		smallSchoolMap.put("一年级", 1);
		smallSchoolMap.put("二年级", 2);
		smallSchoolMap.put("三年级", 3);
		smallSchoolMap.put("四年级", 4);
		smallSchoolMap.put("五年级", 5);
		smallSchoolMap.put("六年级", 6);
		gradeMap.put("小学", smallSchoolMap);
	}
	
	public static Map<String, Integer> getGradeMap(String gradeName) {
		if(gradeName.contains("高")) {
			return(gradeMap.get("高中"));
		}else if(gradeName.contains("初")){
			return(gradeMap.get("初中"));
		}else if(gradeName.contains("七") || gradeName.contains("八") || gradeName.contains("九")){
			return(gradeMap.get("其他"));
		}else{
			return(gradeMap.get("小学"));
		}
		
	}
	
	/**
	 * 获得下一个年级
	 * @param gradeName
	 * @return
	 */
	public static String returnNextGrade(String gradeName) {
		Map<String,Integer> returnMap = getGradeMap(gradeName);
		Integer num = returnMap.get(gradeName);
		
		Iterator<Map.Entry<String, Integer>> entries = returnMap.entrySet().iterator(); 
		while (entries.hasNext()) { 
		  Map.Entry<String, Integer> entry = entries.next(); 
		  if(entry.getValue().intValue()-num.intValue()==1) {
			  return entry.getKey();
		  }
		}
		return null;
	}
	
	/**
	 * 返回上一个年级
	 * @param gradeName
	 * @return
	 */
	public static String returnPreGrade(String gradeName) {
		Map<String,Integer> returnMap = getGradeMap(gradeName);
		Integer num = returnMap.get(gradeName);
		Iterator<Map.Entry<String, Integer>> entries = returnMap.entrySet().iterator(); 
		while (entries.hasNext()) { 
		  Map.Entry<String, Integer> entry = entries.next(); 
		  if(entry.getValue().intValue()-num.intValue()==-1) {
			  return entry.getKey();
		  }
		}
		return null;
	}
	
	/**
	   *   返回当前的年级顺序值
	 * @param gradeName
	 * @return
	 */
	public static Integer returnGradeIndex(String gradeName) {
		Map<String,Integer> returnMap = getGradeMap(gradeName);
		Integer num = returnMap.get(gradeName);
		return num;
	}
}
