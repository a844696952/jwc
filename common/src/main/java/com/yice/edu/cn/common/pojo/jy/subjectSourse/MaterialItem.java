package com.yice.edu.cn.common.pojo.jy.subjectSourse;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;

import java.util.List;

import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*教材章节表
*
*/
@Data
public class MaterialItem{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "父节点id",dataType = "String")
    private String parentId;
    @ApiModelProperty(value = "教材id",dataType = "String")
    private String materialId;
    @ApiModelProperty(value = "是否是叶子节点(1.是 2.否)",dataType = "Integer")
    private Integer leaf;
    @ApiModelProperty(value = "树级别",dataType = "Integer")
    private Integer level;
    @ApiModelProperty(value = "path",dataType = "String")
    private String path;
    @ApiModelProperty(value = "排序",dataType = "Integer")
    private Integer sort;
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
    @NotNull(message = "pager不能为空")
    private Pager pager;
    
    //额外字段
    private List<MaterialItem> children;
}
