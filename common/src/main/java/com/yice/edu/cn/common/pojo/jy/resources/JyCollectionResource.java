package com.yice.edu.cn.common.pojo.jy.resources;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*创建者：陈飞龙。创建时间：2018-11-11。说明：用于存放校本资源
*
*/
@Data
public class JyCollectionResource{

    @ApiModelProperty(value = "我的收藏编号",dataType = "String")
    private String id;
    @ApiModelProperty(value = "讲师编号",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "文件夹编号",dataType = "String")
    private String fileId;
    @ApiModelProperty(value = "资源名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "1:文档 2:图片 3:音频 4:视频",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "资源地址",dataType = "String")
    private String url;
    @ApiModelProperty(value = "资源大小",dataType = "String")
    private String fileSize;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "用于获取文件夹表的父节点",dataType = "long")
    @Transient
    private Long parentId;
    @Transient
    private String[] rowData;
    @Transient
    private String rowDatas[];
    @ApiModelProperty(value = "校本资源的编号，进行关联，用于处理收藏，取消收藏",dataType = "long")
    @Transient
    private String schoolResoucesId;
}
