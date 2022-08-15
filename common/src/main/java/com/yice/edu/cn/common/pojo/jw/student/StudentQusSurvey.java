package com.yice.edu.cn.common.pojo.jw.student;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class StudentQusSurvey {
    @NotNull
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @NotNull
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;

    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
