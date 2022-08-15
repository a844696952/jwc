package com.yice.edu.cn.common.pojo.xw.dutyArrment;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("常规值班安排表")
public class  DutyArrment{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("值班地点")
    private String space;
    @ApiModelProperty("值班地点id")
    private String spaceId;
    @ApiModelProperty("主要负责人")
    private String teacherName;
    @ApiModelProperty("主要负责人id")
    private String teacherId;
    @ApiModelProperty("参与值班的老师id字符串")
    private String attendTeacherId;
    @ApiModelProperty("参与值班的老师名称字符串")
    private String attendTeacher;
    @ApiModelProperty("值班日期id")
    private String dutyDayId;
    @ApiModelProperty("值班日期(forExample:周几)")
    private String dutyDay;
    @ApiModelProperty("值班开始时间(forExample:11:25 )")
    private String dutyTimeStart;
    @ApiModelProperty("值班结束时间(forExample:14:25)")
    private String dutyTimeEnd;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("打卡时间(统一换成分钟数)")
    private String punchTime;
    @ApiModelProperty("值班类型(0:常规值班；1：指定值班)")
    private String dutyType;
    @ApiModelProperty("常规值班开始日期(yyyy-MM-dd)")
    private String rdutyStartDate;
    @ApiModelProperty("常规值班结束日期(yyyy-MM-dd)")
    private String rdutyEndDate;
    @ApiModelProperty("指定日期(yyyy-MM-dd)")
    private String appointedDate;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //字段
    private List<DutyteachersDutyarrment> dutyteachersDutyarrmentCheck;

    @Transient
    private String code;//201
    @Transient
    private String msg;//提醒信息
    private String monthCheckTime;//用于查询个人一个月的值班计划
}
