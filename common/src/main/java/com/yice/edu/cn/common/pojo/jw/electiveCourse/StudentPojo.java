package com.yice.edu.cn.common.pojo.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;

import java.util.List;

@Data
public class StudentPojo extends Student {

    private Integer classesNum;//班号
    private Integer gradeId1;//年级id
    private List<StudentPojo> studentPojoList;//年级id
    private String studentId;
    private String studentName;
    private String classesName;
    private String electiveId;
}
