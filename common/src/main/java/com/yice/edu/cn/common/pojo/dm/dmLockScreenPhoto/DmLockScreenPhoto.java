package com.yice.edu.cn.common.pojo.dm.dmLockScreenPhoto;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("")
public class DmLockScreenPhoto{

    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容，包含了图片，视频地址")
    private String content;
    @ApiModelProperty("发送的类型，1：图片，2：视频")
    private Integer contentType;
    @ApiModelProperty("状态 1：进行中，2：未开始，3：已停止，4：草稿")
    private Integer status;
    @ApiModelProperty("上传时间")
    private String uploadTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("是否为系统相册")
    private String isSystem;
    private String[] ids;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
