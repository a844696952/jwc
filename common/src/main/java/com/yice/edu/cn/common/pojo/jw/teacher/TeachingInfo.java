package com.yice.edu.cn.common.pojo.jw.teacher;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 授课信息
 */
@Data
@Accessors(chain = true)
public class TeachingInfo {
    @Excel(name = "*工号")
    private String workNumber;
    @Excel(name = "*教师名称")
    private String teacherName;
    @Excel(name = "年级")
    private String gradeName;
    @Excel(name = "班级")
    private String classNumber;
    @Excel(name = "科目")
    private String subjectName;
}
