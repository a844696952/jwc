package com.yice.edu.cn.common.pojo.jy.homework;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生查询作业 vo
 */
@Data
public class StudentFindHomeworkVo {
    @ApiModelProperty(value = "状态(1.已提交 2.未提交 3.已提交逾期)",dataType = "int")
    private int status;
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "时间类型(0.本学年、1.本学期、2.近一月内、3.近一周内)",dataType = "int")
    private int type;
    @ApiModelProperty(value = "模糊查询作业名称",dataType = "String")
    private String homeworkName;
    private Pager pager;
}
