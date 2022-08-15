package com.yice.edu.cn.common.pojo.jw.timetable;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("课程表时间片次数")
public class TimetableTime{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "jobId",dataType = "String")
    @NotNull(groups= {GroupOne.class},message="任务Id不能为空")
    private String jobId;
    @ApiModelProperty(value = "weeks",dataType = "Integer")
    @NotNull(groups= {GroupOne.class},message="周次不能为空")
    private Integer weeks;
    @ApiModelProperty(value = "上午次数",dataType = "Integer")
    @NotNull(groups= {GroupOne.class},message="上午次不能为空")
    private Integer ams;
    @ApiModelProperty(value = "下午次数",dataType = "Integer")
    @NotNull(groups= {GroupOne.class},message="下午次不能为空")
    private Integer pms;
    @ApiModelProperty(value = "晚上次数",dataType = "Integer")
    @NotNull(groups= {GroupOne.class},message="晚上次不能为空")
    private Integer nights;
    @ApiModelProperty(value = "createTime",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
