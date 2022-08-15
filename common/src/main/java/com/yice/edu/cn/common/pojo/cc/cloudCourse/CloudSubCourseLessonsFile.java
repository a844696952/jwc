package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("云课堂课件表")
public class CloudSubCourseLessonsFile{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("归属主课程id")
    private String cloudCourseId;
    @ApiModelProperty("归属子课程id")
    private String cloudSubCourseId;
    @ApiModelProperty("归属老师id")
    private String teacherId;
    @ApiModelProperty("课件Id")
    private String lessonsFileId;
    @ApiModelProperty("课件名称")
    private String lessonsFileName;
    @ApiModelProperty("课件路径")
    private String lessonsFilePath;
    @ApiModelProperty("学校")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
