package com.yice.edu.cn.common.pojo.jy.subjectSourse;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*教材
*
*/
@Data
public class Material{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "封面图片",dataType = "String")
    private String image;
    @ApiModelProperty(value = "所属科目年级表id",dataType = "String")
    private String subjectMaterialId;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "创建用户id",dataType = "String")
    private String createUserId;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "修改用户id",dataType = "String")
    private String updateUserId;
    //分页排序等
    @Transient
    private Pager pager;

    private JyMaterialExtend jyMaterialExtend;
}
