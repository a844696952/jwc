package com.yice.edu.cn.common.pojo.jw.timetable;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("课程表任务")
public class TimetableJob{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "name",dataType = "String")
    @NotNull(groups= {GroupOne.class},message="任务名称不能为空")
    private String name;
    @ApiModelProperty(value = "0:常规  预留字段",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "createTime",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "updateTime",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "schoolId",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建人Id",dataType = "String")
    private String createUserId;
    @ApiModelProperty(value = "年级Id，单选",dataType = "String")
    @NotNull(groups= {GroupOne.class},message="年级不能为空")
    private String gradeIds;
    @ApiModelProperty(value = "课程Id，多个用,隔开",dataType = "String")
    @NotNull(groups= {GroupOne.class},message="课程不能为空")
    private String courseIds;
    @ApiModelProperty(value = "1:未排课; 2:已中断; 3:有冲突;4:已完成;99:正在排課",dataType = "Integer")
    private Integer status;
    //分页排序等
    @Transient
    @NotNull(groups= {GroupTwo.class},message = "pager不能为空")
    @Valid
    private Pager pager;
}
