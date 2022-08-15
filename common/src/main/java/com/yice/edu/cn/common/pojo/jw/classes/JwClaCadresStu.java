package com.yice.edu.cn.common.pojo.jw.classes;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
*
*班级的职位的学生
*
*/
@Data
public class JwClaCadresStu{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private String del;
    @ApiModelProperty(value = "职位id",dataType = "String")
    private String claCadresId;
    @ApiModelProperty(value = "班级职务名称",dataType = "String")
    private String claCadresName;
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String studentName;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    
    @ApiModelProperty(value = "学生头像",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "学籍号",dataType = "String")
    private String studentCode;
    @ApiModelProperty(value = "性别",dataType = "String")
    private String sex;
    @ApiModelProperty(value = "所有职位名称",dataType = "String")
    private String claCadresNames;
    @ApiModelProperty(value = "学号",dataType = "String")
    private String studentNo;
    
}
