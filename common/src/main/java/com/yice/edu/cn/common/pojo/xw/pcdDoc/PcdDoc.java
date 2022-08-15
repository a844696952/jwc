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
@ApiModel("区级公文")
public class PcdDoc {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("公文编号")
    private String docNumber;
    @ApiModelProperty("公文标题")
    private String title;
    @ApiModelProperty("已读")
    private Integer read;
    @ApiModelProperty("未读")
    private Integer unRead;
    @ApiModelProperty("总数量")
    private Integer count;
    @ApiModelProperty("通知")
    private String inform;
    @ApiModelProperty("发起通知的用户名称")
    private String informName;
    @ApiModelProperty("多个文件")
    private List<FileName> fileList;
    @ApiModelProperty("发送对象")
    private List<PcdSend> pcdSendList;
    @ApiModelProperty("电教馆id")
    private String eehId;
    @ApiModelProperty("电教馆名称")
    private String eehName;
    @ApiModelProperty("转发公文的原id，流水号")
    private String docId;
    @ApiModelProperty("公文内容")
    private String content;
    @ApiModelProperty("转发类型,0为非转发，1为转发")
    private Integer transmit;
    @ApiModelProperty("创建人id,转发人id")
    private String transmitId;
    @ApiModelProperty("转发平台名称")
    private String transmitName;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "时间段数组，Pc端用来查询时用",dataType = "String[]")
    private String[] searchTimeZone;//时间段数组
    @ApiModelProperty("公文回复数量")
    private Integer size;
    @ApiModelProperty("回复信息")
    private PcdDocRevert pcdDocRevert;

}
