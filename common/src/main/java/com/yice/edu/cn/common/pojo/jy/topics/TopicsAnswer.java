package com.yice.edu.cn.common.pojo.jy.topics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题目答案
 * 可以用于用户作答提交规范提交标准
 */
@Data
public class TopicsAnswer {
    @ApiModelProperty(value = "题目id",dataType = "String")
    private String topicsId;//题目id
    @ApiModelProperty(value = "答案内容(多选则排好序：AB,不能是BA)",dataType = "String")
    private String answer;//答案内容
}
