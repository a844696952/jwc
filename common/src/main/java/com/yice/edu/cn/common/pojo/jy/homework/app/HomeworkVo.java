package com.yice.edu.cn.common.pojo.jy.homework.app;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudentAudioVo;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsAnswer;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户移动端
 * 提交作业等操作
 */
@Data
public class HomeworkVo extends CurSchoolYear {
    @ApiModelProperty(value = "学生作业的id[homeworkstudent.id]",dataType = "String")
    private String homeworkId;//学生作业id
    @NotNull(groups = GroupOne.class)
    @ApiModelProperty(value = "必须填写作业类型 1线上/2线下",dataType = "integer")
    private int homeworkType;//作业类型
    @ApiModelProperty(value = "题目答案 线上作业提交用",dataType = "数组")
    private TopicsAnswer[] topicsAnswers;//题目答案
    @ApiModelProperty(value = "提交的图片 线下作业提交用",dataType = "数组")
    private String[] imgs;//提交图片
    @ApiModelProperty(value = "提交音频 线下作业提交用",dataType = "数组")
    private List<HomeworkStudentAudioVo> homeworkStudentAudioVoList;
    @ApiModelProperty(value = "提交内容",dataType = "String")
    private String content;

}
