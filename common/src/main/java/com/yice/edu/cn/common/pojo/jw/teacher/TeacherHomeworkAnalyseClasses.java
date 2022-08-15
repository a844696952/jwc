package com.yice.edu.cn.common.pojo.jw.teacher;

import lombok.Data;

/**
 * 该对象提供给班级获取任课老师列表和课程信息
 */
@Data
public class TeacherHomeworkAnalyseClasses {
    private String id;
    private String classesId;
    private String tcId; //教师班级id
    private String name;//教师名字
}
