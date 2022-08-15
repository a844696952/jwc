package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class YcStuAddBean {
    @ApiModelProperty(value = "要批量操作的学生列表", dataType = "List")
    ArrayList<Student> studentList;
}
