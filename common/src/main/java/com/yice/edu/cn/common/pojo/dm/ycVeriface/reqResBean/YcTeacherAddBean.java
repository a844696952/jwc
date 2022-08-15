package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;


@Data
public class YcTeacherAddBean {
    @ApiModelProperty(value = "要批量操作的教师列表", dataType = "List")
    ArrayList<Teacher> teacherList;
}
