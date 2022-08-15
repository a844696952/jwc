package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("给学生成绩导出excel用的")
public class StuScoreExcel {
    @Excel(name = "姓名")
    private String studentName;
    @Excel(name = "年级")
    private String gradeName;
    @Excel(name = "班级")
    private String clazzName;
    @Excel(name = "学号")
    private String studentNo;
    @Excel(name = "考号")
    private String examNo;
    @Excel(name = "考试状态")
    private String status;
    @Excel(name = "成绩")
    private Double score;
}
