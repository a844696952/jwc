package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Data
@ApiModel("宿舍楼人员入住详情表")
public class DormBuildingPersonInfo implements Serializable {

    @Transient
    @ApiModelProperty("宿舍床位人员入住id")
    private String id;
    @Transient
    @ApiModelProperty("宿舍类型(1.男生宿舍,2.女生宿舍)")
    private String dormType;
    @Transient
    @ApiModelProperty("宿舍类别(1.学生宿舍,2.老师宿舍,3.宿管宿舍,4.其他人员宿舍)")
    private String dormCategory;
    @Transient
    @ApiModelProperty("宿舍楼id")
    private String dormBuildId;
    @Transient
    @ApiModelProperty("宿舍楼dormBuildIdList集合")
    private List<String> dormBuildIdList;
    @Excel(name="宿舍楼")
    @Transient
    @ApiModelProperty("宿舍楼名称")
    private String dormBuildName;
    @Transient
    @ApiModelProperty("楼层id")
    private String floorId;
    @Excel(name="楼层")
    @Transient
    @ApiModelProperty("楼层名称")
    private String floor;
    @Transient
    @ApiModelProperty("宿舍id")
    private String dormId;
    @Excel(name="宿舍名称")
    @Transient
    @ApiModelProperty("宿舍名称")
    private String dormName;
    @Excel(name="床位")
    @Transient
    @ApiModelProperty("床位名称")
    private String bunkName;
    @Transient
    @ApiModelProperty("父级id")
    private String parentId;
    @Transient
    @ApiModelProperty("人员id")
    private String personId;
    @Transient
    @ApiModelProperty("人员类型")
    private String personType;
    @Excel(name="姓名")
    @Transient
    @ApiModelProperty("人员姓名")
    private String personName;
    @Excel(name="性别")
    @Transient
    @ApiModelProperty("性别")
    private String sex;
    @Excel(name="监护人联系方式")
    @Transient
    @ApiModelProperty("监护人联系方式")
    private String guardianContact;
    @Transient
    @ApiModelProperty("头像")
    private String imgUrl;
    @Excel(name="学号")
    @Transient
    @ApiModelProperty("学号")
    private String studentNo;
    @Transient
    @ApiModelProperty("年级id")
    private String gradeId;
    @Excel(name="年级")
    @Transient
    @ApiModelProperty("年级名称")
    private String gradeName;
    @Transient
    @ApiModelProperty("班级id")
    private String classesId;
    @Excel(name="班号")
    @Transient
    @ApiModelProperty("班号")
    private String number;
    @Transient
    @ApiModelProperty("工号")
    private String workNumber;
    @Transient
    @ApiModelProperty("电话")
    private String tel;
    @Transient
    @ApiModelProperty("所在部门")
    private List<DepartmentTeacher> DepartmentTeacher;
    @Transient
    @ApiModelProperty("学校")
    private String schoolId;
    @Transient
    @ApiModelProperty("班主任姓名")
    private String teacherName;
    @Transient
    @ApiModelProperty("班主任电话")
    private String teacherTel;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    @ApiModelProperty("宿舍床位人员入住id数组 Ids")
    private String[] ids;

    @Transient
    @ApiModelProperty("所在部门字符串")
    private String DepartmentTeacherStr;

}
