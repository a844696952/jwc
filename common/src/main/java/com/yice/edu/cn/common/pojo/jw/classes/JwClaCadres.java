package com.yice.edu.cn.common.pojo.jw.classes;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
*
*班级职位信息
*
*/
@Data
public class JwClaCadres{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private String del;
    @ApiModelProperty(value = "职务名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "职位类型 1系统2自定义",dataType = "Integer")
    private String type;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    
    private String studentName;//学生名称
}
