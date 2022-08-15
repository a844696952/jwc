package com.yice.edu.cn.common.pojo.jw.qusBankResource;

import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

/**
*
* 校本题库？
*
*/
@Data
@Document
public class SchoolQusBank extends Topics {
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建来源",dataType = "String")
    private String createBy;
    @ApiModelProperty(value = "平台题目原有的id",dataType = "String")
    private String originalId;
}
