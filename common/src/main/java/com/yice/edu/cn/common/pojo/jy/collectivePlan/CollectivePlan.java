package com.yice.edu.cn.common.pojo.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*集体备课表
*
*/
@Data
public class CollectivePlan{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "学年",dataType = "String")
    private String schoolYear;
    @ApiModelProperty(value = "备课组名",dataType = "String")
    private String planName;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "发起人id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "1：上学期 2：下学期",dataType = "Integer")
    private Integer semester;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "年级",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "科目",dataType = "String")
    private String subjectName;
    @ApiModelProperty(value = "个人教案id",dataType = "String")
    private String teachingPlanId;
    @ApiModelProperty(value = "提交时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "修改次数",dataType = "Integer")
    private Integer updateCount;
    @ApiModelProperty(value = "评论次数",dataType = "Integer")
    private Integer commentCount;
    @ApiModelProperty(value = "学段id",dataType = "String")
    private String studyingId;
    @ApiModelProperty(value = "学段名称",dataType = "String")
    private String studyingName;
    @ApiModelProperty(value = "已完成备课次数",dataType = "String")
    private String planCount;

    @ApiModelProperty(value = "讨论组教师id，逗号分隔",dataType = "String")
    private String teacherIds;

    @ApiModelProperty(value = "讨论组教师id，逗号分隔",dataType = "String")
    private List<String> teacherIdss;

    @ApiModelProperty(value = "已选择的老师集合",dataType = "String")
    private List<CollectivePlanTeacher> collectivePlanTeacher;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
