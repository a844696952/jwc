package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("")
public class KqMonthStatistical{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("考勤月份")
    private String kqMonth;
    @ApiModelProperty("学生姓名")
    private String name;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("年级")
    private String gradeName;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("班号")
    private String classesNumber;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("迟到次数")
    private String lateNum;
    @ApiModelProperty("早退次数")
    private String leaveEarlyNum;
    @ApiModelProperty("缺卡次数")
    private String missNum;
    @ApiModelProperty("座号")
    private String seatNumber;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private Student student;

    private String[] duskOutStatus;

    private String[] morningInStatus;

    private String[] morningOutStatus;

    private String[] noonInStatus;

    private Integer studentNum;//总打卡人数

    //存放1迟到人数
    @ApiModelProperty("迟到人数")
    private Integer lateManNum=0;
    //存放2早退人数
    @ApiModelProperty("早退人数")
    private Integer leaveEarlyManNum=0;
    //存放3缺卡人数
    @ApiModelProperty("缺卡人数")
    private Integer  missManNum=0;
}
