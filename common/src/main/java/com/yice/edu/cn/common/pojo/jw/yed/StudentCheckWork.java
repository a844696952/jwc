package com.yice.edu.cn.common.pojo.jw.yed;


import lombok.Data;

import java.util.ArrayList;

/**
 *
 *学生考勤情况
*/
@Data
public class StudentCheckWork {
    private String month; //月份
    private Integer normal;//考勤正常
    private Integer late;//迟到人数
    private Integer miss;//缺卡人数
    private Integer early;//早退人数
    private String startDate;//筛选开始时间
    private String endDate;//筛选结束时间
    private String educationBureauId;//教育局id
    private ArrayList<String > months;//存储总的月份
    private ArrayList<Integer> normals;
    private ArrayList<Integer> lates;
    private ArrayList<Integer> misses;
    private ArrayList<Integer> earlys;

 }
