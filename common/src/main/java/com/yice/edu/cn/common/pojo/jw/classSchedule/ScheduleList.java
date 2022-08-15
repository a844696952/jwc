package com.yice.edu.cn.common.pojo.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("课表管理列表")
public class ScheduleList {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("学年名称")
    private String fromTo;
    @ApiModelProperty("学年id")
    private String fromToId;
    @ApiModelProperty("0代表上学期，1代表下学期")
    private Integer term;
    @ApiModelProperty("学校Id")
    private String schoolId;
    @ApiModelProperty("0-未发布，1-已发布，2-已过期")
    private Integer type;
    @ApiModelProperty("创建用户id")
    private String userId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
