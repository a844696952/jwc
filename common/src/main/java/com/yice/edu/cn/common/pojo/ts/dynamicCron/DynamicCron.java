package com.yice.edu.cn.common.pojo.ts.dynamicCron;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("动态任务规则列表")
public class DynamicCron{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("定时任务时间表达式")
    private String cron;
    @ApiModelProperty("任务类型,0定时跟新动态任务列表 1学生出入校定时教师推送")
    private String type;
    @ApiModelProperty("任务描述")
    private String taskDescription;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty("学校id")
    private String schoolId;
}
