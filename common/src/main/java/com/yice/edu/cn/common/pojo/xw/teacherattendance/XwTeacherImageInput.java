package com.yice.edu.cn.common.pojo.xw.teacherattendance;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("老师图像录入")
public class XwTeacherImageInput {

    @ApiModelProperty(value = "id", dataType = "String")
    private String id;
    @ApiModelProperty(value = "状态(1是海康已录入，2是中移动已录入)", dataType = "String")
    private String status;
    @ApiModelProperty(value = "缩略图", dataType = "String")
    private String img;
    @ApiModelProperty(value = "教师id", dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "学校id", dataType = "String")
    private String schoolId;

    @Transient
    @ApiModelProperty(value = "教师名称", dataType = "String")
    private String teacherName;

    @Transient
    @ApiModelProperty(value = "工号", dataType = "String")
    private String workers;

    @Transient
    @ApiModelProperty(value = "打卡机返回的状态码", dataType = "String")
    private String machineStatus;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
