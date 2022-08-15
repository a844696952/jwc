package com.yice.edu.cn.common.pojo.jw.practice;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
*
*
*
*/
@Data
public class Practice{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "实践开始日期",dataType = "String")
    private String practiceStartdate;
    @ApiModelProperty(value = "实践结束日期",dataType = "String")
    private String practiceEnddate;
    @ApiModelProperty(value = "实践内容",dataType = "String")
    private String practiceContent;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    @Transient
    private String[] rangeTime;
    @ApiModelProperty(value = "参与教师",dataType = "String")
    private List<PracticeTeacher> practiceTeacherCheck;
    @ApiModelProperty(value = "参与教师数量",dataType = "Integer")
    private Integer teacherNum;
    @ApiModelProperty(value = "文件列表",dataType = "String")
    private List<PracticeFile> files;
    @ApiModelProperty(value = "实践人",dataType = "String")
    private String practiceTeacherName;
    @ApiModelProperty(value = "实践人id",dataType = "String")
    private String practiceTeacherId;


}
