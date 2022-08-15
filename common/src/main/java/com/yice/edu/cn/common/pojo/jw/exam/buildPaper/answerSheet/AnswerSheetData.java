package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.pojo.validateClass.GroupSix;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "答题卡的数据类,包含答题卡的坐标,基本信息,答案和分值等,等同于大题",description = "客户端上传数据时,typeName,typeId,subjective,answerSheetItems,reviewTeachers")
public class AnswerSheetData {
    @ApiModelProperty(value = "题型名称",dataType = "String")
    @NotBlank(message = "题型名称不能为空",groups = GroupSix.class)
    private String typeName;
    @ApiModelProperty(value = "题型id",dataType = "String")
    @NotNull(message = "题型id不能为空",groups = GroupSix.class)
    private Integer typeId;
    @ApiModelProperty(value = "是否主观题",dataType = "Boolean")
    @NotNull(message = "是否主观题不能为空",groups = GroupSix.class)
    private Boolean subjective;
    @ApiModelProperty("大题题目的标题")
    @NotNull(message = "大题题目的标题不能为空",groups = GroupSix.class)
    private String title;
    @ApiModelProperty(value = "小题列表",dataType = "List")
    @NotEmpty(message = "小题列表不能为空",groups = GroupSix.class)
    @Valid
    private List<AnswerSheetItem> answerSheetItems;
    @ApiModelProperty(value = "阅卷老师集合",dataType = "List")
    private List<ReviewTeacher> reviewTeachers;
    @ApiModelProperty("大题坐标集合")
    private TopicPosition[] topicPositions;
    @ApiModelProperty("该大题是否已阅,指底下所有小题都已批阅。创建答题卡时，此字段无用")
    private Boolean reviewed;
    @ApiModelProperty("客户端对主观题进行切图后上传保存的地址。创建答题卡时，此字段无用")
    private String img;



    @ApiModelProperty(value = "老师已批阅完某门科目的某种类型大题的对象名单",dataType = "Object")
    private List<String> reviewedTeacherIds;
    @ApiModelProperty(value = "批阅此学生成绩大题的对应教师")
    private String teacherName;



}
