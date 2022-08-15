package com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;


@Data
@ApiModel("倒计时管理")
public class DmCountDownManage {

    @ApiModelProperty(value = "id", dataType = "String")
    private String id;
    @ApiModelProperty(value = "倒计时名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "倒计时的终止时间", dataType = "String")
    private String breakTime;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间", dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "展示状态（0已结束，1展示中）", dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "学校id", dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //距离倒计时的天数
    private Integer number;

    //状态转换成布尔值
    private Boolean result;

}
