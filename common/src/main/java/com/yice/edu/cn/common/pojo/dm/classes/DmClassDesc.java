package com.yice.edu.cn.common.pojo.dm.classes;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
/**
*
*班级简介表
*
*/
@Data
public class DmClassDesc{
    @ApiModelProperty(value = "编号",dataType = "String")
    private String id;//主键id
    @ApiModelProperty(value = "简介",dataType = "String")
    private String des;//简介
    @ApiModelProperty(value = "班级Id",dataType = "String")
    private String classId;//班级id
    @ApiModelProperty("班级别名")
    private String classAlias;
    @ApiModelProperty("班级标语")
    private String classLogan;
    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;//学校id
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
