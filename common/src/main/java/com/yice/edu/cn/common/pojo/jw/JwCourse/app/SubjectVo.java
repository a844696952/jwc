package com.yice.edu.cn.common.pojo.jw.JwCourse.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 科目 提供给app用
 */
@Data
public class SubjectVo {
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "科目名称",dataType = "String")
    private String subjectName;
    @ApiModelProperty(value = "科目下新增的数量",dataType = "Integer")
    private Integer newCount;
}
