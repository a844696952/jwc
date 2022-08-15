package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;


@Data
@ApiModel("职工月考勤统计")
public class KqWorkerManageMonth {
    @ApiModelProperty("迟到人员列表")
    private List<KqWorkerMonth> allLateManDetailList;
    @ApiModelProperty("早退人员列表")
    private List<KqWorkerMonth> allEarlyManDetailList;
    @ApiModelProperty("缺卡人员列表")
    private List<KqWorkerMonth> allMissManDetailList;
    @ApiModelProperty("补卡人员列表")
    private List<KqWorkerMonth> allFillMissManDetailList;
    @ApiModelProperty("旷工人员列表")
    private List<KqWorkerMonth> allAbsenceManDetailList;
    @ApiModelProperty("请假人员列表")
    private List<KqWorkerMonth> allLeaveManDetailList;
    @ApiModelProperty("公出人员列表")
    private List<KqWorkerMonth> allOfficialOutManDetailList;
    @ApiModelProperty("出差人员列表")
    private List<KqWorkerMonth> allOutManDetailList;
    //考勤组长查看内容
    @ApiModelProperty("迟到人数")
    private Integer lateManNum;
    @ApiModelProperty("早退人数")
    private Integer earlyManNum;
    @ApiModelProperty("缺卡人数")
    private Integer missManNum;
    @ApiModelProperty("旷工人数")
    private Integer absenceManNum;
    @ApiModelProperty("补卡人数")
    private Integer fillMissManNum;
    @ApiModelProperty("请假人数")
    private Integer leaveManNum;
    @ApiModelProperty("公出人数")
    private Integer officialOutManNum;
    @ApiModelProperty("出差人数")
    private Integer outManNum;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //考勤组长查看内容
    @ApiModelProperty("迟到总次数")
    private Integer allLateNum;
    @ApiModelProperty("早退总次数")
    private Integer allEarlyNum;
    @ApiModelProperty("缺卡总次数")
    private Integer allMissNum;
    @ApiModelProperty("旷工总次数")
    private Integer allAbsenceNum;
    @ApiModelProperty("补卡总次数")
    private Integer allFillMissNum;
    @ApiModelProperty("请假总次数")
    private Integer allLeaveNum;
    @ApiModelProperty("公出总次数")
    private Integer allOfficialOutNum;
    @ApiModelProperty("出差总次数")
    private Integer allOutNum;
    @ApiModelProperty("请假做多类型")
    private String rifenessLeaveType;
    @ApiModelProperty("参与考勤总人数")
    private Integer allKqMan;
    @ApiModelProperty("异常人数")
    private Integer allWrongMan;
    @ApiModelProperty("异常人数")
    private Double WrongPercentage;
}
