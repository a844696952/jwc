package com.yice.edu.cn.common.pojo.jy.source21.model.question;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 21世纪查询题目列表参数bean
 */
@ApiModel(value = "查询题目列表参数bean")
@Data
@Accessors(chain = true)
public class SearchParam {
    @ApiModelProperty(value = "学段id")
    private Integer stage;
    @ApiModelProperty(value = "科目id-必填",dataType = "Integer")
    private Integer subjectId;
    @ApiModelProperty(value = "章节id-必填，多个用,隔开",dataType = "String")
    private String chapterId;
    @ApiModelProperty(value = "知识点id-必填，多个用,隔开",dataType = "String")
    private String knowledgeId;
    @ApiModelProperty(value = "题型，多个用,隔开",dataType = "String")
    private String baseType;
    @ApiModelProperty(value = "题目难度，多个用,隔开",dataType = "String")
    private String difficult;
    @ApiModelProperty(value = "题类，多个用,隔开",dataType = "String")
    private String examType;
    @ApiModelProperty(value = "题目类型，多个用,隔开",dataType = "String")
    private String type;
    @ApiModelProperty(value = "查询平台",dataType = "String")
    private String platform;
    @ApiModelProperty(value = "关键字",dataType = "String")
    private String keyword;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @NotNull
    private Pager pager;
}
