package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学生未到校记录对象")
public class StuNotComeToSchool{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("当前学年id")
    private String schoolYearId;
    @ApiModelProperty("学年")
    private String fromTo;
    @ApiModelProperty("0表示上学期,1下学期")
    private Integer term;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("学生姓名")
    private String studentName;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("班级编号")
    private String classesNumber;
    @ApiModelProperty("学校上课时间")
    private String classStartTime;
    @ApiModelProperty("学校下课时间")
    private String classEndTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private String[] timeZone;
}
