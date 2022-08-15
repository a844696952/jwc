package com.yice.edu.cn.common.pojo.xw.visitorManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("访客")
public class Visitor {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("访问者姓名")
    private String visitorName;
    @Pattern(regexp = "^1\\d{10}$", message = "联系方式必须为11位手机号码")
    @ApiModelProperty("访问者电话")
    private String visitorTel;
    @Pattern(regexp = "^[1-9]\\\\d{5}(18|19|([23]\\\\d))\\\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\\\d{3}[0-9Xx]$", message = "证件号码必须为18位")
    @ApiModelProperty("访问者身份证号")
    private String visitorCard;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("访问事由")
    private String visitorReason;
    @ApiModelProperty("访问者头像")
    private String visitorImg;
    @ApiModelProperty("访问类型 0家长 1陌生人")
    private String visitorType;
    @ApiModelProperty("审批人")
    private String auditor;
    @ApiModelProperty("审批人id")
    private String auditorId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("结束时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("访问设备")
    private String devices;
    @ApiModelProperty("申请结果 0通过 1不通过 2未审批（审批中） 3已关闭  4已审批")
    private String applyStatus;
    @ApiModelProperty("不通过原因")
    private String unpassReason;
    @ApiModelProperty("访问途径  0刷脸  1人证")
    private String visitorWay;
    @ApiModelProperty("家长id")
    private String parentId;
    @ApiModelProperty("亲属关系")
    private String relationShip;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("学生姓名")
    private String studentName;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("二维码")
    private byte[] qrCode;
    @ApiModelProperty("教师名字")
    private String teacherName;
    @ApiModelProperty("教师Id")
    private String teacherId;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
    private String[] searchTime;
}

