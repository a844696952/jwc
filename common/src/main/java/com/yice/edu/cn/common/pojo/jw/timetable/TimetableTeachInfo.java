package com.yice.edu.cn.common.pojo.jw.timetable;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;


import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("任课信息")
public class TimetableTeachInfo{

	@ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "jobId",dataType = "String")
    private String jobId;
    @ApiModelProperty(value = "gradeId",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "gradeName",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "classId",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "班号",dataType = "String")
    private String classesName;
    @ApiModelProperty(value = "课程Id",dataType = "String")
    private String courseId;
    @ApiModelProperty(value = "courseName",dataType = "String")
    private String courseName;
    @ApiModelProperty(value = "每周上课次数",dataType = "Integer")
    private Integer count;
    @ApiModelProperty(value = "多个用逗号隔开",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "多个用逗号隔开",dataType = "String")
    private String teacherName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    
    
}
