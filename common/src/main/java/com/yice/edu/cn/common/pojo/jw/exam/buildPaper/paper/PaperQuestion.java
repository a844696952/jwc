package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * 试卷大题
 *
 */
@Document
@Data
public class PaperQuestion implements Serializable {
    private static final long serialVersionUID = 6740342397400691045L;
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "题目类型",dataType = "Integer")
    private Integer typeId;
    @ApiModelProperty(value = "题目名称",dataType = "String")
    private String typeName;
    @ApiModelProperty(value = "总分数",dataType = "Double")
    private Double scoreNumber;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "创建人id",dataType = "String")
    private String createUserId;
    @ApiModelProperty(value = "题目列表",dataType = "Object")
    private List<PaperTopics> topicsList;    //题目列表
    @ApiModelProperty(value = "试卷Id",dataType = "String")
    private String  testPaperId;    //试卷Id
    @ApiModelProperty(value = "题目对象",dataType = "Object")
    private PaperTopics paperTopics;    //题目
    @ApiModelProperty(value = "标准字段（1.为单选2.为非单选）",dataType = "Integer")
    private Integer topicsType;   //标准字段（1.为单选2.为非单选）
    @ApiModelProperty(value = "大题描述",dataType = "String")
    private String subjectText="描述"; //大题描述
    @ApiModelProperty(value = "排序字段",dataType = "Integer")
    private Integer sort=1;//暂不使用
    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;

    @ApiModelProperty(value = "大题包含的小题数量",dataType = "Integer")
    private Integer size;

    @ApiModelProperty(value = "大题标题")
    private String title;


}
