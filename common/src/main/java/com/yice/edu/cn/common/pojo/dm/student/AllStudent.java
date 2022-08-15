package com.yice.edu.cn.common.pojo.dm.student;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;

import java.util.List;

@Data
public class AllStudent extends JwClasses {

    /**
     * 0 班级 1 学生
     * */
    private String type;

    private List<Student> studentList;
}
