package com.yice.edu.cn.common.pojo.xw.document;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Data
public class DocManagement {
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间（可以用来记录教师的最新查看时间）",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识符",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "收文查看对象id",dataType = "String")
    private String docObjectId;
    @ApiModelProperty(value = "收文查看对象名称",dataType = "String")
    private String docObjectName;
    @ApiModelProperty(value = "收文管理id",dataType = "String")
    private String docId;
    @ApiModelProperty(value = "1为未查看，2为查看",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "是否是抄送人",dataType = "Boolean")
    private Boolean flag;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String[] searchTimeZone;//时间段筛选

    @ApiModelProperty("departmentName，父名称")
    private String departmentName;
    @ApiModelProperty("departmentParentId,父Id")
    private String departmentParentId;
    @ApiModelProperty("类型:0为组织架构，1为成员")
    private Integer departmentType;
    @ApiModelProperty("发送人头像")
    private String imgUrl;
    @ApiModelProperty("父路径")
    private String path;
}
