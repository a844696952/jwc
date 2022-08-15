package com.yice.edu.cn.common.pojo.xw.stuLeaveManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("学生请假表")
public class StuLeave extends CurSchoolYear{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("请假类型 0事假 1病假")
    private String type;
    @ApiModelProperty("发起方  0家长  1班主任")
    private String applicatiorType;
    @ApiModelProperty("与孩子关系")
    private String stuRelationship;
    @ApiModelProperty("班主任姓名")
    private String teacherName;
    @ApiModelProperty("班主任联系方式")
    private String teacherTel;
    @ApiModelProperty("班主任id")
    private String teacherId;
    @ApiModelProperty("家长联系方式")
    private String parentsTel;
    @ApiModelProperty("家长id")
    private String parentsId;
    @ApiModelProperty("状态 0在校 1请假(在校) 2请假(离校) 3请假")
    private String status;
    @ApiModelProperty("是否离校 0是  1否 ")
    private String inOrOutSchool;
    @ApiModelProperty("开始时间")
    private String beginTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("请假原因")
    private String leaveReasons;
    @ApiModelProperty("不通过原因")
    private String unpassReasons;
    @ApiModelProperty("审批状态 0通过 1不通过 2(tap待审批 bmp审批中) 3已关闭")
    private String approveStatus;
     //分页
    /*@Transient
    @NotNull(message = "pager不能为空")
    private Pager pager;*/
    private Student student;//学生对象
    private String studentName;//学生姓名
    @Transient
    private String[] rangeTime;
}
