package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("宿舍申请表")
public class HouseApplican{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("发起时间")
    private String createTime;
    @ApiModelProperty("申请类型 0住宿  1留宿")
    private String applicanType;
    @ApiModelProperty("住宿/留宿开始时间")
    private String startTime;
    @ApiModelProperty("住宿/留宿结束时间")
    private String endTime;
    @ApiModelProperty("申请理由")
    private String remark;
    @ApiModelProperty("发起人id")
    private String initiateId;
    @ApiModelProperty("发起人姓名")
    private String initiateName;
    @ApiModelProperty("发起人电话")
    private String initiateTel;
    @ApiModelProperty("发起端口  0教师 1家长")
    private String initiatePort;
    @ApiModelProperty("申请单最终状态  0待审批  1审批成功  2驳回")
    private String status;
    @ApiModelProperty("当前进度  记录老师审批位置")
    private String progress;
    @ApiModelProperty("班主任id")
    private String headTeacherId;
    @ApiModelProperty("家长端用以判断当前孩子,用来判断展示的数据")
    private String stuId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    //日期查询字段
    private String[] searchTimeZone;
    private String searchStearTime;
    private String searchEndTime;

    List<HouseApplicanFiles> houseApplicanFiles;
    List<HouseApplicanStudents> houseApplicanStudents;
    List<String> houseApplicanTeachers;

    List<HouseApplicanTeachers> houseApplicanTeachers2;

    private String nowTeacherId;   //当前操作教师id   yml中使用
    private String teacherRemark;  //审批提交的备注
    private String teacherSort;    //当前审批人的权重
    private String teacherNextSort;    //当前审批人的下一权重
    private String teacherStatus;  //判断前端点的是 同意 还是 驳回
    private String houseTeacherId; //教师对象表 教师数据的id
    private String studentsId;    //移动端 直接传学生id
    private String studentsName;    //移动端  用于查看页面

    private String teacherImg;   //手机展示
    private String studentImg;
    private String nextTeacherId;  //下一教师id  用以推送
}
