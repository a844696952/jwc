package com.yice.edu.cn.common.pojo.jy.topics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 查询题目的vo
 */
@Data
@Accessors(chain = true)
public class TopicParam {
    @ApiModelProperty(value = "题目来源")
    private String source;
    @ApiModelProperty(value = "题目id")
    private String id;
    @ApiModelProperty(value = "教师id")
    private String teacherId;
    @ApiModelProperty(value = "学校")
    private String schoolId;
}
