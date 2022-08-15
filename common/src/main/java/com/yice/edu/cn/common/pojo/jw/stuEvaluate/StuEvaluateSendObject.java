package com.yice.edu.cn.common.pojo.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
*
*
*
*/
@Data
@Document
public class StuEvaluateSendObject{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "评价发送对象",dataType = "String")
    private StuEvaluate stuEvaluate;
    @Indexed
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "教师姓名",dataType = "String")
    private String teacherName;
    @Indexed
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "班号",dataType = "String")
    private String classNumber;
    @Indexed
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "班级总人数",dataType = "Integer")
    private Integer classPeoNum;
    @ApiModelProperty(value = "提交人数",dataType = "Integer")
    private Integer submitNum;
    @ApiModelProperty(value = "创建时间",dataType = "Integer")
    private String createTime;
    @ApiModelProperty("schoolId")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "1、进行中 2、截止 ",dataType = "String")
    @Transient
    private String state;
    @ApiModelProperty("当前学年的id")
    private String schoolYearId;
    @ApiModelProperty("学年")
    private String fromTo;
    @ApiModelProperty("0表示上学期,1下学期")
    private Integer term;
}
