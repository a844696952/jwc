package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-04 09:42
 * @Description: 人像录入对象
 */
@Data
public class KqTeacherAddBean {
    @ApiModelProperty(value = "要批量操作的教师列表", dataType = "List")
    ArrayList<Teacher> teacherList;
}
