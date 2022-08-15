package com.yice.edu.cn.common.pojo.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("学校节数时间表")
public class   ClassScheduleInit{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("课表第一节，从1开始")
    private Integer number;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("0为默认数据，1为学校数据")
    private Integer type;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}
