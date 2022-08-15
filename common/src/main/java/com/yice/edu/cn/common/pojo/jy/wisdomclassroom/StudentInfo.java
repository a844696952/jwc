package com.yice.edu.cn.common.pojo.jy.wisdomclassroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生答案对象
 */

@Data
public class StudentInfo implements Serializable {

    @ApiModelProperty(value = "学生名称",dataType = "String")
    private String studentName;
    @ApiModelProperty(value = "学生ID",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "是否答对",dataType = "String")
    private Boolean isRight;

}
