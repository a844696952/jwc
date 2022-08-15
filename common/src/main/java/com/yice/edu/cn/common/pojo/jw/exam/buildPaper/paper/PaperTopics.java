package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 *
 * 题目类
 *
 */



@Data
@Document
public class PaperTopics extends Topics {


    @ApiModelProperty(value = "分数",dataType = "Double")
    private Double score=1.0;       //分数
    @ApiModelProperty(value = "是否显示",dataType = "Boolean")
    private  Boolean isShow;


    @ApiModelProperty(value = "创建人id",dataType = "String")
    private String createUserId; //创建人Id
    @ApiModelProperty(value = "试卷id",dataType = "String")
    private String testPaperId;//试卷id
    @ApiModelProperty(value = "题目来源（0-运营平台，1-校本资源，2-个人题库）",dataType = "Integer")
    private Integer topicSource; //题目来源

    @ApiModelProperty(value = "学校Id",dataType = "String")
    private String schoolId;

}
