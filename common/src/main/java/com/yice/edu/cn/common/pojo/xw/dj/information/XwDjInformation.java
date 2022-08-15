package com.yice.edu.cn.common.pojo.xw.dj.information;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@ApiModel("党建资讯表")
public class XwDjInformation{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("栏目ID")
    private String columnId;
    @ApiModelProperty("标题")
    @Size(max = 100, message = "标题最长限制不得超过100字")
    private String informationTitle;
    @ApiModelProperty("内容")
    @Size(max = 10000, message = "内容最长限制不得超过一万字")
    private String informationContent;
    @ApiModelProperty("状态（未发布--0/已发布--1/关闭--2")
    private Byte informationState;
    @ApiModelProperty("是否展示资源信息（0-不展示 1-视频2-音频）")
    private Byte isShowRes;
    @ApiModelProperty("创建日期")
    private String createDate;
    @ApiModelProperty("创建人ID")
    private String creatorId;
    @ApiModelProperty("操作人ID")
    private String operatorId;
    @ApiModelProperty("操作时间")
    private String operateDate;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;



    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private List<XwDjAttachmentFile> xwDjAttachmentFiles;
    private String operatorName;
    @Transient
    private String classifyName;
    /**
     * 是否是管理员 0--不是 1--是
     */
    @Transient
    private String isAdmin;
}
