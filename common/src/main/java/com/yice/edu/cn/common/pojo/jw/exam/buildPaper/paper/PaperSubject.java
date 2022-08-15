package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 用来存储上一次选择的科目
 *
 */

@Data
public class PaperSubject {
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建人Id",dataType = "String")
    private String createUserId;
    @ApiModelProperty(value = "科目Id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "科目名称",dataType = "String")
    private String subjectName;
    @ApiModelProperty(value = "一级知识点Id",dataType = "String")
    private String oneKnowledgeId;
    @ApiModelProperty(value = "二级知识点Id",dataType = "String")
    private String twoKnowledgeId;
    @ApiModelProperty(value = "三级知识点Id",dataType = "String")
    private String knowledgeId;
    @ApiModelProperty(value = "三级知识点名称",dataType = "String")
    private String knowledgeName;
    @ApiModelProperty(value = "题目来源",dataType = "Integer")
    private Integer topicSource;
    @ApiModelProperty(value = "题目类型",dataType = "Integer")
    private Integer typeId;
}
