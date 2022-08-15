package com.yice.edu.cn.common.pojo.xw.stuLeaveManage;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

import java.util.List;

@Data
@ApiModel("学生请假通知人员表")
public class NotifyPerson {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("教师姓名")
    private String teacherName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页
    @Transient
    private Pager pager;

    private String[] departmentName;//所属部门
}
