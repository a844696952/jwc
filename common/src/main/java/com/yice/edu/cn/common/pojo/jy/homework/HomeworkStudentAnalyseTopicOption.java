package com.yice.edu.cn.common.pojo.jy.homework;

import lombok.Data;

import java.util.List;

@Data
public class HomeworkStudentAnalyseTopicOption {

    private Integer optionId;
    private String optionName;
    private List<String> homeworkAnalyseStudentName;//作业分析每道题的学生名字
    private List<String> homeworkAnalyseStudentAnswer;//作业分析每道题的学生答案
}
