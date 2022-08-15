package com.yice.edu.cn.common.pojo.xq.analysisStudentScore;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.security.acl.Group;

@ApiModel(value = "考试学生学情查询vo")
@Data
public class AnalysisVo {
    @NotNull(groups = {GroupThree.class},message = "考试id不能为空")
    @ApiModelProperty(value = "考试id-必填",dataType = "String")
    private String examinationId;
    @ApiModelProperty(value = "科目课程id-选填",dataType = "String")
    @NotNull(groups = {GroupOne.class, GroupTwo.class,GroupThree.class},message = "科目课程id不能为空")
    private String subjectId;
    @NotNull(groups = {GroupThree.class},message = "班级id不能为空")
    @ApiModelProperty(value = "班级id-选填",dataType = "String")
    private String classId;
    @NotNull(groups = {GroupTwo.class},message = "学生id不能为空")
    @ApiModelProperty(value = "学生id-选填",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "查询全部成绩的时候是否要展示各个科目的考试成绩，默认false-选填",dataType = "Boolean")
    private boolean showAll = false;
    @NotNull(groups = {GroupThree.class},message = "知识点id不能为空")
    @ApiModelProperty(value = "知识点ID-选填",dataType = "String")
    private String knowledgeId;
    @ApiModelProperty(value = "题号",dataType = "Integer")
    private Integer topicNum;
    //分页排序等
    private Pager pager;
}
