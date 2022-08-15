package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;



@Api("坐标和数据")
@Data
public class PostilTopciPosition {
    //坐标
    private TopicPosition[] topicPositionList;
    //题目总分
    private Double score;
    //学生成绩
    private Double youScore;
    //题目类型
    private Integer typeId;
    //批注
    private String postil;
}
