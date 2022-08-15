package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.oa.process.OaPeople;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.xw.workerKq
 * @Author: Administrator
 * @CreateTime: 2019-05-14 10:43
 * @Description: ${Description}
 */
@ApiModel(value = "考勤日统计打卡时间点状态详情  4 请假(0 事假 1年假 2病假 3 婚假 4 生育假 5 陪产假 6 丧假 7 会议请假 8 其他 9调休" +
        "5 补卡(1 上班补卡1 2 上班补卡2 3 下班补卡1  4 下班补卡2) 6 出差  7 公出")
@Data
@Document
public class KqDailyStatusDetail extends CurSchoolYear {
    // 开始时间、结束时间、时长、申请人、申请时间、最终审批人、最终审批时间、请假类型、补卡点、补卡时间
    @ApiModelProperty(value = "id", dataType = "String")
    private String id;
    @ApiModelProperty(value = "状态", dataType = "String")
    private String status;
    @ApiModelProperty(value = "状态类型", dataType = "String")
    private String statusType;
    @ApiModelProperty(value = "开始时间", dataType = "String")
    private String beginTime;
    @ApiModelProperty(value = "结束时间", dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "时长", dataType = "String")
    private String timeSpan;
    @ApiModelProperty(value = "申请人", dataType = "String")
    private String applicant;
    @ApiModelProperty(value = "申请人id", dataType = "String")
    private String applicantId;
    @ApiModelProperty(value = "申请时间", dataType = "String")
    private String applyTime;
    @ApiModelProperty(value = "审批人", dataType = "String")
    private List<OaPeople> approver;
    @ApiModelProperty(value = "最终审批时间", dataType = "String")
    private String lastApprTime;
    @ApiModelProperty(value = "补卡点", dataType = "String")
    private String fillMissCardPoint;
    @ApiModelProperty(value = "补卡时间", dataType = "String")
    private String fillUpTime;
    @ApiModelProperty(value = "学校id", dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "数据来源 0手动修改 1 同步OA", dataType = "String")
    private String source;
    @Transient
    @ApiModelProperty(value = "操作人Id", dataType = "String")
    private String operatorMan;
    @Transient
    @ApiModelProperty(value = "时间范围", dataType = "String")
    private String[] rangeTime;
}
