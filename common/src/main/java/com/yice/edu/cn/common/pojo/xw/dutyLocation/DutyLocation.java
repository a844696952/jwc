package com.yice.edu.cn.common.pojo.xw.dutyLocation;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("值班地点管理")
public class DutyLocation{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("值班地点")
    private String place;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private String code;
    private String msg;
}
