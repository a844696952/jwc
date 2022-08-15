package com.yice.edu.cn.common.pojo.jy.wisdomclassroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * 选项统计实体对象
 */
@Data
public class OptionStatistics implements Serializable {

    @ApiModelProperty(value = "选项名称",dataType = "String")
    private String optionName;
    @ApiModelProperty(value = "学生信息")
    List<StudentInfo> studentAnswerList;
    @ApiModelProperty(value = "选项类型  0--作答选项 1--未答选项")
    Integer optionType;

}
