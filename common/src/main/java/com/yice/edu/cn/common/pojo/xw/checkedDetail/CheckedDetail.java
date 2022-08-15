package com.yice.edu.cn.common.pojo.xw.checkedDetail;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment.DutyteachersDutyarrment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("签到明细")
public class CheckedDetail  extends CurSchoolYear implements Serializable {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("记录日期(app查询时)")
    @Excel(name = "值班日期",isImportField = "true_checkedDetail")
    private String recordDate;
    @ApiModelProperty("签到地点")
    @Excel(name = "值班地点",isImportField = "true_checkedDetail")
    private String dutyArrmentSpace;
    @ApiModelProperty("值班安排记录id")
    private String dutyArrmentId;
    @ApiModelProperty("值班开始时间(forExample:11:25 )")
    @Excel(name = "值班开始时间",isImportField = "true_checkedDetail")
    private String dutyTimeStart;
    @ApiModelProperty("值班结束时间(forExample:14:25 )")
    @Excel(name = "值班结束时间",isImportField = "true_checkedDetail")
    private String dutyTimeEnd;
    @ApiModelProperty("教师姓名")
    @Excel(name = "教师姓名",isImportField = "true_checkedDetail")
    private String teacherName;
    @ApiModelProperty("手机号")
    @Excel(name = "手机号",isImportField = "true_checkedDetail")
    private String tel;
    @ApiModelProperty("是否签到( 0:未签到,1：已签到 ,2:迟到)")
    @Excel(name = "是否签到",replace = {"未签到_0","已签到_1","迟到_2"})
    private String signIn;
    @ApiModelProperty("签到时间")
    @Excel(name = "签到时间",isImportField = "true_checkedDetail")
    private String timeCheckIn;
    @ApiModelProperty("值班负责人")
    @Excel(name = "值班负责人",isImportField = "true_checkedDetail")
    private String dutyOfficer;
    @ApiModelProperty("值班日期id")
    private String dutyDayId;
    @ApiModelProperty("值班日期(forExample:周几)")
    private String dutyDay;
    @ApiModelProperty("参与值班的老师名称字符串")
    private String attendTeacher;
    @ApiModelProperty("值班负责人id")
    private String dutyOfficerId;
    @ApiModelProperty("值班地点id")
    private String spaceId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("值班老师的头像路径")
    private String teacherImgul;
    @ApiModelProperty("经度")
    private String lon;
    @ApiModelProperty("纬度")
    private String lat;
    @ApiModelProperty("签到地点(app)")
    private String signInPlace;
    @ApiModelProperty("签到类型1:打卡签到 2.拍照签到")
    private String checkType;
    @ApiModelProperty("签到图片")
    private String signInPicture;
    @ApiModelProperty("签到日期")
    private String dateCheckIn;

    @ApiModelProperty("签到明细时间")
    private String dateTimeChecked;
    @ApiModelProperty("签到备注")
    private String checkInRemarks;

    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("允许开始打卡时间(forExample:2019-01-09 11:05)")
    private String punchTimeLater;
    @ApiModelProperty("允许开始打卡时间(forExample:11:05)")
    private String punchTime;
    @ApiModelProperty("值班类型(0:常规值班；1：指定值班)")
    private String dutyType;
    @ApiModelProperty("是否同步常规值班(0:否1：是)")
    private String dutyAfterSyn;
    @ApiModelProperty("常规值班开始日期(yyyy-MM-dd)")
    private String rdutyStartDate;
    @ApiModelProperty("常规值班结束日期(yyyy-MM-dd)")
    private String rdutyEndDate;
    @ApiModelProperty("参与值班的老师id字符串")
    private String attendTeacherId;
    //分页排序等
    @Transient
    private String findStartTime; //查询开始时间
    private String findEndTime; //查询结束时间
    private String year;    //年
    private String month;  //月
    private String timeType; //查询时间状态(1 本月 2 上个月 3 本学期)
    private long recordTime; //具体时间

    private List<CheckedDetail> checkedDetailList; //app
    private String monthCheckTime;//按月查询值班次数
    private String changedDutye;//变更的值班日期

    //字段
    private List<DutyteachersDutyarrment> dutyteachersDutyarrmentCheck;

    private String code;
    private String msg;
    private String ids;//值班明细id字符串
    private String puchTimeLaterNew;//最新的打卡时间
    private String dutyDayNew;//最新的星期几
    private String dutyDayIdNew;//最新的星期几id
    private List<String> delIds;//id
    private String punTime;//比较时间
}