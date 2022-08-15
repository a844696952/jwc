package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("教职工考勤每日统计表   0迟到 1早退 2正常 3缺卡 4 请假 5补卡 6出差 7公出")
@Document
public class KqWorkerDaily extends CurSchoolYear {

    @ApiModelProperty("教师日统计记录主键")
    private String id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户类型1教师2职工")
    private String userType;
    @ApiModelProperty("考勤分组id")
    private String groupId;
    @ApiModelProperty("考勤分组名称")
    private String groupName;
    @ApiModelProperty("用户工号")
    private String worknumber;
    @ApiModelProperty("考勤日期")
    private String kqDate;
    @ApiModelProperty("是否旷工0否1是")
    private String isAbsence;
    @ApiModelProperty("考勤详情")
    private PunchRules punchRules;
    @ApiModelProperty("部门名称")
    private String departmentName;
    @ApiModelProperty("部门id")
    private String departmentId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty(value = "打卡次数 1 一天一回 2 一天两回", dataType = "String")
    private String punchNumber;
    @ApiModelProperty(value = "今天是否放假,0否 1是", dataType = "String")
    private String todayIsHoliday;
    @ApiModelProperty(value = "今天的考勤标准规则", dataType = "String")
    private WorkerKqRules todayStandardRules ;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime ;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime ;
    @Transient
    @ApiModelProperty("手机打卡范围半径")
    private Integer radius;
    @Transient
    @ApiModelProperty("下次打卡时间")
    private String nextClockTime;
    @Transient
    @ApiModelProperty(value = "移动端打卡  默认0 开启1", dataType = "String")
    private String appPunchCard;
    @Transient
    @ApiModelProperty(value = "本次打卡位置", dataType = "String")
    private KqLocation theKqLocation;
    @Transient
    @ApiModelProperty(value = "学校位置", dataType = "String")
    private KqLocation schoolLocation;
    @Transient
    @ApiModelProperty(value = "今日是否已完成打卡任务,0未完成 1已完成", dataType = "String")
    private String clockInFinished;
    @Transient
    @ApiModelProperty(value = "今日是否需要参与考勤打卡,0不需要 1需要", dataType = "String")
    private String todayNeedClockIn;
    @Transient
    @ApiModelProperty("管理员查看统计用考勤组id列表")
    private List<String> groupIdList;
    @Transient
    @ApiModelProperty("管理员查看统计用考勤人员id列表")
    private List<String> personsIdArr;
    @Transient
    @ApiModelProperty("需要展示哪几个卡的状态，配合打卡状态3判断")
    private List<String> needShowCard;
    @Transient
    @ApiModelProperty("此次打卡时间")
    private String captureTime;
    @Transient
    @ApiModelProperty("用户头像")
    private String userImgUrl;
    @Transient
    @ApiModelProperty("是否批量")
    private Boolean isBatch;

}

