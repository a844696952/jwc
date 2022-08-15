package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("阅卷老师")
public class ReviewTeacher {
    @ApiModelProperty("教师id")
    private String id;
    @ApiModelProperty("教师名字")
    private String name;
    @ApiModelProperty("教师电话号码")
    private String tel;
    @ApiModelProperty("阅卷时答题卡份数的范围,包头不包尾")
    private int[] answerSheetRange;


}
