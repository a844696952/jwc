package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("职工管理员查看当前考勤统计情况")
public class KqWorkerManageNow {
    @ApiModelProperty("早上入校迟到人员列表")
    private List<KqWorkerDaily> morningInLatePeople;
    @ApiModelProperty("早上入校缺卡人员列表")
    private List<KqWorkerDaily> morningInMissPeople;
    @ApiModelProperty("中午入校迟到人员列表")
    private List<KqWorkerDaily> noonInLatePeople;
    @ApiModelProperty("中午入校缺卡人员列表")
    private List<KqWorkerDaily> noonInMissPeople;
    @ApiModelProperty("早上离校早退人员列表")
    private List<KqWorkerDaily> morningOutEarlyPeople;
    @ApiModelProperty("早上离校缺卡人员列表")
    private List<KqWorkerDaily> morningOutMissPeople;
    @ApiModelProperty("傍晚离校早退人员列表")
    private List<KqWorkerDaily> daskOutEarlyPeople;
    @ApiModelProperty("傍晚离校缺卡人员列表")
    private List<KqWorkerDaily> daskOutMissPeople;

    @ApiModelProperty("早上入校迟到人数")
    private Integer morningInLateNum;
    @ApiModelProperty("早上入校缺卡人数")
    private Integer morningInMissNum;
    @ApiModelProperty("中午入校迟到人数")
    private Integer noonInLateNum;
    @ApiModelProperty("中午入校缺卡人数")
    private Integer noonInMissNum;
    @ApiModelProperty("早上离校早退人数")
    private Integer morningOutEarlyNum;
    @ApiModelProperty("早上离校缺卡人数")
    private Integer morningOutMissNum;
    @ApiModelProperty("傍晚离校早退人数")
    private Integer daskOutEarlyNum;
    @ApiModelProperty("傍晚离校缺卡人数")
    private Integer daskOutMissNum;

    @ApiModelProperty("当日考勤打卡次数")
    private String punchNumber;


    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
