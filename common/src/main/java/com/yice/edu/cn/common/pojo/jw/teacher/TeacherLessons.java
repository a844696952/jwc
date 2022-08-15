package com.yice.edu.cn.common.pojo.jw.teacher;

import io.swagger.annotations.Api;
import lombok.Data;

@Api("教师授课信息")
@Data
public class TeacherLessons {
    private String id;
    private String className;
    private String classId;
    private String gradeName;
    private String gradeId;
    private String courseId;
    private String courseName;
}
