package com.yice.edu.cn.common.pojo.dm.modeManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Document
public class ExamTask {

    @Id
    @Indexed
    private String taskId;

    @ApiModelProperty(value = "任务名称",dataType = "String")
    private String taskName;

    @ApiModelProperty(value = "开始时间",dataType = "String")
    @NotNull
    private String beginTime;

    @ApiModelProperty(value = "结束时间",dataType = "String")
    @NotNull
    private String endTime;

    @ApiModelProperty(value = "创建日期",dataType = "String")
    private String createDate;

    @ApiModelProperty(value = "年级ID",dataType = "String")
    private String gradeId;

    @ApiModelProperty(value = "任务类型 1--通知  2--考试   3--上课  4--欢迎"  )
    private Integer taskType;

    @ApiModelProperty(value = "排序优先级")
    private Integer order;

    @ApiModelProperty(value = "当前绑定班牌名称 唯一")
    private List<String> userName;

    @ApiModelProperty(value = "当前状态 0--初始状态 1--进行中 2--关闭")
    private Integer status;

    @ApiModelProperty(value = "推送日期")
    private String pushDate;

    @ApiModelProperty(value = "学校ID")
    private String schoolId;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}
