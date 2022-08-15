package com.yice.edu.cn.common.pojo.jw.practice;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PracticeTeacher {
    @ApiModelProperty(value = "id" ,dataType = "String")
    private String id;
    @ApiModelProperty(value = "实践id" ,dataType = "String")
    private String practiceId;
    @ApiModelProperty(value = "教师id" ,dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "教师姓名" ,dataType = "String")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "离职教师姓名" ,dataType = "String")
    private String quitname;
    @ApiModelProperty(value = "离职教师id" ,dataType = "String")
    private String quitteacherId;
}
