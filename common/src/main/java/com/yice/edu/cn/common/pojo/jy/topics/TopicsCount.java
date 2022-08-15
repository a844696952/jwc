package com.yice.edu.cn.common.pojo.jy.topics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * 题目做题统计
 * 目前整合作业使用
 */
@Data
public class TopicsCount {
    @ApiModelProperty(value = "题目Id",dataType = "String")
    private String id;//题目Id
    @ApiModelProperty(value = "正确人数",dataType = "Integer")
    private Integer correctNum;//正确人数
    @ApiModelProperty(value = "错误人数",dataType = "Integer")
    private Integer wrongNum;//错误人数

    @Transient
    private Integer topicNumber; //每次作业的题号
}
