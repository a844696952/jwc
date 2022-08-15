package com.yice.edu.cn.common.pojo.jw.student;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用于学生班级信息导入
 */
@Data
@EqualsAndHashCode(of ={"studentNo","name"})
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassInfo {

    @Excel(name="* 学号")
    private String studentNo;//学号

    @Excel(name = "* 姓名")
    private String name;//姓名

    @Excel(name="* 所在年级")
    private String gradeName;//年级名称

    @Excel(name="* 所在班级")
    private String classesNumber;//班号


}
