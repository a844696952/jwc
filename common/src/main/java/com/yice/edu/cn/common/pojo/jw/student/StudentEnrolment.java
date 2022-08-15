package com.yice.edu.cn.common.pojo.jw.student;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
*
*升学记录表
*
*/
@Data
public class StudentEnrolment {

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "升学前的班级总人数",dataType = "String")
    private String studentCount;
    @ApiModelProperty(value = "升学后的班级总人数",dataType = "String")
    private String graduationCount;
    @ApiModelProperty(value = "留级班级人数",dataType = "String")
    private String detainedGradeCount;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "班级应届年份",dataType = "String")
    private String enrollYear;
    @ApiModelProperty(value = "升学年份",dataType = "String")
    private String enrolmentYear;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
