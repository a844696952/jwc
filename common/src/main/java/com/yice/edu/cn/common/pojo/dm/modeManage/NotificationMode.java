package com.yice.edu.cn.common.pojo.dm.modeManage;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@ApiModel("通知模式表")
public class NotificationMode{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("通知标题")
    @Size(max = 20)
    private String title;
    @ApiModelProperty("通知内容")
    @Size(max = 300)
    private String content;
    @ApiModelProperty("展示状态 0--展示中 1-- 待展示 2--已结束 3--已关闭")
    private Integer showStatus;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("任务ID")
    private String taskId;
    @ApiModelProperty("创建时间")
    private String createTime;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    private Long timeStamp;
}
