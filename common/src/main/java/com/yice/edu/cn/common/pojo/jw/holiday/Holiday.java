package com.yice.edu.cn.common.pojo.jw.holiday;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author:xushu
 * @date:2019/5/8
 */
@Data
@Document
@ApiModel("节假日")
public class Holiday {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("状态 0生成 1新增")
    private String status;
    @ApiModelProperty("年份")
    private String year;
    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("类型 0放假 1上班")
    private String type;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    private String[] allDate;
    private String startTime;
    private String endTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private HolidayDate holidayDate;

}