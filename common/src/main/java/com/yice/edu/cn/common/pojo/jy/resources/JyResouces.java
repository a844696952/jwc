package com.yice.edu.cn.common.pojo.jy.resources;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
*
*创建时间：2018-10-29。说明：用于存放我的资源
*
*/
@Data
public class JyResouces{


    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "如果为空则为个人资源，否则为平台资源",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "文件夹编号",dataType = "String")
    private String fileId;
    @ApiModelProperty(value = "资源的url",dataType = "String")
    private String url;
    @ApiModelProperty(value = "资源名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "1:文档 2:图片 3:音频 4:视频 5:试卷",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "浏览量",dataType = "Long")
    private Long browseNumber;
    @ApiModelProperty(value = "下载量",dataType = "Long")
    private Long downloadNumber;
    @ApiModelProperty(value = "点赞量",dataType = "Long")
    private Long commendNumber;
    @ApiModelProperty(value = "上传时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "上传文件的大小",dataType = "String")
    private String fileSize;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "分享状态，默认为：0：未分享，1：已分享",dataType = "Integer")
    private Integer shareStatus;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "用于获取文件夹表的父节点",dataType = "long")
    @Transient
    private Long parentId;
    @Transient
    private String rowData[];
    @Transient
    private String rowDatas[];

    private int cnt = 0;

}
