package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class QusSurveyClass {

    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String classesName;
    @ApiModelProperty(value = "班级应届年份")
    private String enrollYear;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private Integer gradeId;
    @Transient
    private List<QusSurveyClass> classList;
}
