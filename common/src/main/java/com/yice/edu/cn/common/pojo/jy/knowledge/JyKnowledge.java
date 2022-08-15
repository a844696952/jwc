package com.yice.edu.cn.common.pojo.jy.knowledge;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
*
*知识点树
*
*/
@Data
public class JyKnowledge{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "父节点id",dataType = "String")
    private String parentId;
    @ApiModelProperty(value = "类型(1.年级 2.科目 3.知识点)",dataType = "String")
    private String type;
    @ApiModelProperty(value = "树级别",dataType = "String")
    private String level;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除判断1.正常 2.删除",dataType = "String")
    private String del;
    @ApiModelProperty(value = "是否是叶子结点(1.叶子结点 2.非叶子结点)",dataType = "String")
    private String leaf;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
