package com.yice.edu.cn.common.pojo.jw.student;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 *
 *
 */
@Data
public class JwStudentGraduation implements Serializable {

    private static final long serialVersionUID = -7777849139559240431L;

    @ApiModelProperty(value = "id唯一标识符",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识符 1为正常 2为删除",dataType = "Integer")
    private Integer del;
    @Excel(name = "学生姓名")
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String name;
    @ApiModelProperty(value = "学生性别",dataType = "String")
    private String sex;
    @ApiModelProperty(value = "图片路径",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "邮箱",dataType = "String")
    private String email;
    @ApiModelProperty(value = "国籍",dataType = "String")
    private String nationality;
    @ApiModelProperty(value = "民族名称",dataType = "String")
    private String nationName;
    @ApiModelProperty(value = "民族",dataType = "String")
    private String nation;
    @ApiModelProperty(value = "籍贯",dataType = "String")
    private String nationPlace;
    @ApiModelProperty(value = "省id",dataType = "String")
    private String provinceId;
    @ApiModelProperty(value = "省份名称",dataType = "String")
    private String provinceName;
    @ApiModelProperty(value = "市id",dataType = "String")
    private String cityId;
    @ApiModelProperty(value = "城市名称",dataType = "String")
    private String cityName;
    @ApiModelProperty(value = "区/县id",dataType = "String")
    private String countyId;
    @ApiModelProperty(value = "区/县名称",dataType = "String")
    private String countyName;
    @ApiModelProperty(value = "详细地址",dataType = "String")
    private String address;
    @ApiModelProperty(value = "政治面貌",dataType = "String")
    private String politicsFace;
    @ApiModelProperty(value = "出生日期",dataType = "String")
    private String birthday;
    @ApiModelProperty(value = "学号",dataType = "String")
    private String studentNo;
    @ApiModelProperty(value = "入学时间",dataType = "String")
    private String admissionDate;
    @ApiModelProperty(value = "学籍号",dataType = "String")
    private String studentCode;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;
    @Excel(name="毕业班级")
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String classesName;
    @Excel(name="毕业年份")
    @ApiModelProperty(value = "毕业时间",dataType = "String")
    private String graduationTime;
    @ApiModelProperty(value = "毕业年级",dataType = "String")
    private String graduationGrade;
    @ApiModelProperty(value = "毕业班级",dataType = "Integer")
    private String graduationClass;
    @ApiModelProperty(value = "原毕业学生表Id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "毕业时年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "学生应届年份",dataType = "String")
    private String enrollYear;
    @Excel(name="家长联系电话")
    @ApiModelProperty(value = "监护人联系方式",dataType = "String")
    private String guardianContact;//监护人联系方式
    @ApiModelProperty(value = "职务Id",dataType = "String")
    private String postId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private String nativePlace;  //用于毕业籍贯转换


}
