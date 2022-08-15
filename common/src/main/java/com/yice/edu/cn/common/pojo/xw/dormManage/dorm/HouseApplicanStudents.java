package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("宿舍申请学生对象表")
public class HouseApplicanStudents{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("createTime")
    private String createTime;
    @ApiModelProperty("申请表id")
    private String houseApplicanId;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("0默认  1宿管同意 还未分配住宿  2已办理住宿")
    private String type;

    @ApiModelProperty("添加时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String studentsName;
    private String types;
    List<Student>  studentList;

    List<HouseApplicanStudents> houseApplicanStudentsList;



    private String imgUrl;//头像
    private String gradeId;//年级id
    private String classesId;//班级di
    private String guardianContact;//监护人联系方式
    @Excel(name = "姓名")
    private String name;//姓名
    @Excel(name = "学号")
    private String studentNo;//学号
    @Excel(name = "性别",replace = {"男_4","女_5"})
    private String sex;//性别
    @Excel(name = "年级")
    private String gradeName;//年级名称
    @Excel(name = "班号")
    private String classesNumber;//班号
    @ApiModelProperty("宿舍楼id")
    private String dormitoryId;
    @Excel(name = "宿舍楼")
    @ApiModelProperty("宿舍楼名字")
    private String dormitoryName;
    @ApiModelProperty("楼层id")
    private String floorId;
    @Excel(name = "楼层")
    @ApiModelProperty("楼层名字")
    private String floorName;
    @ApiModelProperty("宿舍id /场地")
    private String bedRoomId;
    @Excel(name = "宿舍名称")
    @ApiModelProperty("宿舍名字")
    private String bedRoomName;
    @Excel(name = "床位")
    @ApiModelProperty("床号")
    private String bedCode;


    List<DormBuildingPersonInfo> dormBuildingPersonInfoList;

    private String studentsId;

}
