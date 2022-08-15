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
public class JySchoolResouces{

    @ApiModelProperty(value = "校本资源编号",dataType = "String")
    private String id;
    @ApiModelProperty(value = "学校编号",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "讲师编号 ",dataType = "String")
    private String teacherId;
    @ApiModelProperty("资源编号")
    private String resoucesId;
    @ApiModelProperty(value = "文件夹根目录编号,默认为0则代表的是根目录",dataType = "String")
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
    @ApiModelProperty(value = "结束时间",dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "开始时间",dataType = "String")
    private String startTime;
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
    @ApiModelProperty(value = "该字段由我的收藏里面查询出来，如果已经收藏了改资源，则有值，否则无值",dataType = "long")
    @Transient
    private String schoolResoucesId;
}
