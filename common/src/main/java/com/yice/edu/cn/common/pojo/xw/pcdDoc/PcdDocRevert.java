package com.yice.edu.cn.common.pojo.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@ApiModel("公文回复")
public class PcdDocRevert {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("回复平台id")
    private String eehId;
    @ApiModelProperty("回复账号来源")
    private String eehName;
    @ApiModelProperty("公文id")
    private String docId;
    @ApiModelProperty("回复内容")
    private String content;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("是否已读 0-未读 1-已读")
    private Integer type;
    @ApiModelProperty("文件")
    private List<FileName> fileList;
    @ApiModelProperty("回复账号名称")
    private String name;
    @ApiModelProperty("回复账号id")
    private String createUserId;
    @ApiModelProperty("总回复数")
    private Integer size;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "时间段数组，Pc端用来查询时用",dataType = "String[]")
    private String[] searchTimeZone;//时间段数组
}
