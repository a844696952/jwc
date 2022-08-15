package com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("党建附件表")
public class XwDjAttachmentFile{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件路径")
    private String filePath;
    @ApiModelProperty("1--文档 2-图片 - 3-音频 4--视频")
    private Integer fileType;
    @ApiModelProperty("引用ID 对应相关模块的主键")
    private String referenceId;
    @ApiModelProperty("文件描述")
    private String fileDesc;
    @ApiModelProperty("创建日期")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
