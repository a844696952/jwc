package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("开始时间")
public class StuInAndOutStartTime {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("createTime")
    private String createTime;
    @ApiModelProperty("updateTime")
    private String updateTime;
    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("startTime")
    private String startTime;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    private Pager pager;
}
