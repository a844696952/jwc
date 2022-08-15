package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("人员入住信息日志")
public class DormPersonLog implements Serializable {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("宿舍人员类型（1.学生，2.老师，3.非教师职工）")
    private String personType;
    @ApiModelProperty("宿舍人员id")
    private String personId;
    @ApiModelProperty("宿舍楼名称")
    private String dormBuildName;
    @ApiModelProperty("楼层")
    private String floor;
    @ApiModelProperty("宿舍名称")
    private String dormName;
    @ApiModelProperty("床位名称")
    private String bunkName;
    @ApiModelProperty("办理时间")
    private String optime;
    @ApiModelProperty("办理类型(0.住宿,1.调宿,2.退宿)")
    private String optType;
    @ApiModelProperty("退宿备注")
    private String remarks;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    /*-----------------------*/
    @Transient
    @ApiModelProperty("人员姓名")
    private String personName;
    @Transient
    @ApiModelProperty("性别")
    private String sex;
    @Transient
    @ApiModelProperty("监护人联系方式")
    private String guardianContact;
    @Transient
    @ApiModelProperty("头像")
    private String imgUrl;
    @Transient
    @ApiModelProperty("学号")
    private String studentNo;
    @Transient
    @ApiModelProperty("工号")
    private String workNumber;
    @Transient
    @ApiModelProperty("电话")
    private String tel;
    @Transient
    @ApiModelProperty("年级")
    private String gradeName;
    @Transient
    @ApiModelProperty("班号")
    private String number;
    @Transient
    @ApiModelProperty("班主任")
    private String teacherName;
    @Transient
    @ApiModelProperty("班主任电话")
    private String teacherTel;
    @Transient
    @ApiModelProperty("所在部门")
    private List<DepartmentTeacher> DepartmentTeacher;
    @Transient
    private String startTime;
    @Transient
    private String endTime;
    @Transient
    @ApiModelProperty("退宿时间集合")
    private List<String> searchTimeZone;
    @Transient
    @ApiModelProperty("班级id")
    private String classesId;
}
