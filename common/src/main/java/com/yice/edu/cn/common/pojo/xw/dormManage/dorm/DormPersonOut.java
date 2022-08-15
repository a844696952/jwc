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
@ApiModel("退宿人员表")
public class DormPersonOut{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("退宿人员id")
    private String personId;
    @ApiModelProperty("退宿人员姓名")
    private String personName;
    @ApiModelProperty("退宿人员类型(1.学生,2.老师,3.非教职工)")
    private String personType;
    @ApiModelProperty("性别(4.男,5.女)")
    private String sex;
    @ApiModelProperty("监护人联系方式")
    private String guardianContact;
    @ApiModelProperty("学号")
    private String studentNo;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("班主任")
    private String teacherName;
    @ApiModelProperty("班主任电话")
    private String teacherTel;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("工号")
    private String workNumber;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("所属部门")
    private String departments;
    @ApiModelProperty("退宿日期")
    private String outTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    /*=================*/
    @Transient
    private String startTime;
    @Transient
    private String endTime;
    @Transient
    @ApiModelProperty("退宿时间集合")
    private List<String> searchTimeZone;
}
