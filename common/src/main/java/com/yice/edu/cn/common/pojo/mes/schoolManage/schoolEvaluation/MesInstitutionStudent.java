package com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;

import java.util.List;

@Data
public class MesInstitutionStudent extends JwClasses {
    private String type;//0 -> 班级
    private String beginTime;
    private String endTime;
    private String institutionId;
    private List<Student> studentList;
    private Integer score;

}
