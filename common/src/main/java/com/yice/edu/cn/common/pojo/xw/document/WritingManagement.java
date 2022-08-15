package com.yice.edu.cn.common.pojo.xw.document;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*发文管理--发文对象查看表
*
*/
@Data
public class WritingManagement{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识符",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "发文查看对象id",dataType = "String")
    private String writingObjectId;
    @ApiModelProperty(value = "发文查看对象名称(父名称)",dataType = "String")
    private String writingObjectName;
    @ApiModelProperty(value = "发文管理id",dataType = "String")
    private String writingId;
    @ApiModelProperty(value = "1为未查看，2为查看",dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty("departmentParentId,父Id")
    private String departmentParentId;
    @ApiModelProperty("类型:0为组织架构，1为成员")
    private Integer departmentType;
    @ApiModelProperty("发送人头像")
    private String imgUrl;
    @ApiModelProperty("父路径")
    private String path;


}
