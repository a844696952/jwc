package com.yice.edu.cn.common.pojo.jw.electiveCourse;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;


@Data
@ApiModel("选修学生关联表")
public class ElectiveStudent extends CurSchoolYear {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("学生姓名")
    @Excel(name = "姓名",width=20)
    private String studentName;
    @Excel(name="学号",width=30)
    @ApiModelProperty(value = "学号",dataType = "String")
    private String studentNo;
    @ApiModelProperty("选修id")
    @Excel(name="electiveId",isColumnHidden=true)
    private String electiveId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("入班时间")
    @Excel(name = "入班时间",width=30)
    private String joinTime;
    @ApiModelProperty("年级id")
    private String gradeId;
    @Excel(name = "年级名称")
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("班级名称")
    @Excel(name = "班级")
    private String classesName;
    @ApiModelProperty("性别 4男  5女")
    @Excel(name = "性别", replace = { "男_4", "女_5" })
    private String sex;
    @ApiModelProperty("成绩")
    @Excel(name = "成绩")
    private String result;


    @ApiModelProperty("是否获得学分  0否 1是")
    @Excel(name = "是否获得学分", replace = { "否_0", "是_1","_null" })
    private Integer isScore;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("学分 ")
    private Integer score;
    @ApiModelProperty("可选课程集合")
    List<ElectiveCourse> canSelectList;
    @ApiModelProperty("已选课程集合")
    List<ElectiveCourse> alreadySelectList;
    @Transient
    @ApiModelProperty("已选数量")
    private Integer alreadySelectNum;
    @Transient
    @ApiModelProperty("最大数量")
    private Integer maxNum;
    @Transient
    @ApiModelProperty("最小数量")
    private Integer minNum;
    @Transient
    @ApiModelProperty("已选课名称")
    private String alreadyName;
    @Transient
    @ApiModelProperty("选课状态 0未选满 1已选满")
    private String alreadySelectStatus;
    @Transient
    @ApiModelProperty("选修课名称")
    private String electiveName;
    @Transient
    private String name;

    @ApiModelProperty("上课时间（星期）")
    private String classTimeWeek;
    @ApiModelProperty("第几节")
    private String classTimeSection;
    @ApiModelProperty("课程开始时间")
    private String courseStartTime;
    @ApiModelProperty("课程结束时间")
    private String courseEndTime;
}
