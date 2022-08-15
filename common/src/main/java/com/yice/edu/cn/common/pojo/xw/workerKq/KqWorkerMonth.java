package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import lombok.Data;
import org.mvel2.util.Make;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Data
@ApiModel("职工月考勤统计")
public class KqWorkerMonth{

    @ApiModelProperty("id")
    private String userId;
    @ApiModelProperty("教职工姓名")
    private String userName;
    @ApiModelProperty("部门")
    private String departmentName;
    @ApiModelProperty("部门id")
    private String departmentId;
    @ApiModelProperty("考勤分组id")
    private String groupId;
    @ApiModelProperty("考勤分组名称")
    private String groupName;
    @ApiModelProperty("工号")
    private String worknumber;
    @ApiModelProperty("osp查看缺卡次数")
    private Integer missCardNum;
    @ApiModelProperty("请假明细")
    private String  leaveMsg;
    @ApiModelProperty("总加班时长")
    private Float workOvertime;
    @ApiModelProperty("考勤日期")
    private String kqDate;
    @ApiModelProperty("日期内容map")
    private HashMap<String,String> dateAndMsgMap;
    @ApiModelProperty("早退时间区间和次数")
    private HashMap<String,Integer> earlyMap;
    @ApiModelProperty("迟到时间区间和次数")
    private HashMap<String,Integer> lateMap;
    @ApiModelProperty(value = "学校Id", dataType = "String")
    private String schoolId;
    //考勤人员查看内容
    @ApiModelProperty("迟到次数")
    private Integer lateNum;
    @ApiModelProperty("早退次数")
    private Integer earlyNum;
    @ApiModelProperty("缺卡次数")
    private Integer missNum;
    @ApiModelProperty("旷工次数")
    private Integer absenceNum;
    @ApiModelProperty("补卡次数")
    private Integer fillMissNum;
    @ApiModelProperty("请假次数")
    private Integer leaveNum;
    @ApiModelProperty("公出次数")
    private Integer officialOutNum;
    @ApiModelProperty("出差次数")
    private Integer outNum;
    @ApiModelProperty("迟到详情")
    List<HashMap<String,Object>> theManLateDetailList ;
    @ApiModelProperty("早退详情")
    List<HashMap<String,Object>> theManEarlyDetailList;
    @ApiModelProperty("缺卡详情")
    List<HashMap<String,Object>> theManMissDetailList;
    @ApiModelProperty("补卡详情")
    List<HashMap<String,Object>> theManFillMissDetailList;
    @ApiModelProperty("旷工详情")
    List<HashMap<String,Object>> theManAbsenceDetailList;
    @ApiModelProperty("请假详情")
    private  List<KqDailyStatusDetail> theManLeaveDetailList;
    @ApiModelProperty("公出详情")
    private  List<KqDailyStatusDetail> theManOfficialOutDetailList;
    @ApiModelProperty("出差详情")
    private  List<KqDailyStatusDetail> theManOutDetailList;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty("管理员查看统计用考勤组id列表")
    private List<String> groupIdList;
    @Transient
    @ApiModelProperty("教职工列表")
    private ArrayList<SchoolNotifySendObject> schoolNotifySendObjectList;
    @Transient
    @ApiModelProperty("通知记录的时间")
    private String manageSendTime;
    @Transient
    @ApiModelProperty("教师职位Id")
    private String postId;
    @Transient
    @ApiModelProperty("状态次数")
    private Integer theManStatusNum;

}
