package com.yice.edu.cn.common.pojo.xw.dj;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberFile.XwDjAttachmentFile;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("学习资源")
public class XwDjStudyResource {

    @ApiModelProperty(value = "主键id", dataType = "String")
    private String id;
    @ApiModelProperty(value = "类型id", dataType = "String")
    private String type;
    @ApiModelProperty(value = "活动类型", dataType = "String")
    private String activityType;
    @ApiModelProperty(value = "标题", dataType = "String")
    private String title;
    @ApiModelProperty(value = "内容", dataType = "String")
    private String content;
    @ApiModelProperty(value = "发布时间", dataType = "String")
    private String publishTime;
    @ApiModelProperty(value = "添加时间", dataType = "String")
    private String addTime;
    @ApiModelProperty(value = "已学习人数", dataType = "Integer")
    private Integer studyNumber;
    @ApiModelProperty(value = "发送给教师的人数", dataType = "Integer")
    private Integer teacherNumber;
    @ApiModelProperty(value = "教师id", dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "教师名称", dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "学校id", dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "是否发布的状态标志（0未发布，1发布，2关闭）", dataType = "Integer")
    private Integer state;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //对应的文件资源
    @Transient
    private List<XwDjAttachmentFile> fileList;
    //类型名称
    private String typeName;
}
