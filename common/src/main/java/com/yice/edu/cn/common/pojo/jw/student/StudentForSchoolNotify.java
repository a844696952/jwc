package com.yice.edu.cn.common.pojo.jw.student;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.validateClass.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

@Data
public class StudentForSchoolNotify {
    @ApiModelProperty(value = "主键id",dataType = "String")
    @NotBlank(message = "学生id不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String id;//id
    @NotBlank(message = "姓名不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class, GroupFive.class})  //GroupTwo 添加  GroupThree修改
    @Excel(name = "学生姓名",isImportField = "true_student")
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String name;//姓名
    @ApiModelProperty(value = "年级id",dataType = "String")
    @NotBlank(message = "年级id不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class})
    private Integer gradeId;//年级id

    @ApiModelProperty(value = "年级名称",dataType = "String")
    @NotBlank(message = "年级不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class})
    @Excel(name="年级")
    private String gradeName;//年级名称
    @ApiModelProperty(value = "班级id",dataType = "String")
    @NotBlank(message = "班级不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class})
    private String classesId;//班级di
    @ApiModelProperty(value = "班号",dataType = "String")
    @Excel(name="班号")
    @NotBlank(message = "学生班号不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String classesNumber;//班号
    @ApiModelProperty("头像 ")
    private String imgUrl;
    private String schoolId;//班号

    private String enrollYear;
    private ArrayList<String> classIdList;


}
