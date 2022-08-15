package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("内容附件表")
public class XwCmsContentFile {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("1-普通附件 2--封面 3--视频文件4--图片")
    private Integer referenceType;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("内容Id")
    private String referenceId;
    @ApiModelProperty("文件描述")
    private String fileDesc;
    @ApiModelProperty("文件类型1-文档2-图片 3-视频 4-音频")
    private Integer fileType;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
