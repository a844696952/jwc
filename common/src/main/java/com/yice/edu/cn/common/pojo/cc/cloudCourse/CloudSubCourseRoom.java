package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;

@Data
public class CloudSubCourseRoom {
    private String id;
    private String name;
    private String inTime;
    private String outTime;
    private int alertTime;
    private boolean allowListen;
    private String createTeacherId;
    private String createTeacherName;
    private String createTeacherTel;
    private long teacherCount;
    private String createTime;
    private String duration;
    private String broadcastCode;
    private String schoolId;
    private String teacherId;
    private String teacherName;
    private String teacherTel;
    private String parentName;
}
