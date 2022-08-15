package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import lombok.Data;

import java.util.List;

@Data
public class CloudCoursewareVo {
    private List<CloudSubCourseLessonsFile> lessonsFileList;
    private String cloudSubCourseId;
}
