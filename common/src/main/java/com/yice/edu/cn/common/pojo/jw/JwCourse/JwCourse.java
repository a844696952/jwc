package com.yice.edu.cn.common.pojo.jw.JwCourse;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.common.pojo.validateClass.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;

/**
 * 课程信息,查询课程请带入学校Id
 */
@Data
@ApiModel(value = "课程")
public class JwCourse implements Serializable {

    private static final long serialVersionUID = 6054352053973220975L;

    @NotNull(message = "用户id不能为空", groups = {GroupOne.class, GroupSix.class, GroupSeven.class})//GroupOne为修改,GroupTwo为添加,GroupThree为分页
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;//主键id

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间

    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;//更新时间

    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;//删除标识

    @ApiModelProperty(value = "课程名称--对应的数据字典中数据类型的id",dataType = "String")
    @NotNull(message = "课程id不能为空", groups = {GroupOne.class, GroupSix.class, GroupSeven.class})
    private String nameId;//课程名称--对应的数据字典中数据类型的id

    @ApiModelProperty(value = "课程名称--其他时自己输入的值，或者课程类型名称",dataType = "String")
    @NotNull(message = "课程名称不能为空", groups = {GroupOne.class})
    private String name;//课程名称--其他时自己输入的值，或者课程类型名称

    @ApiModelProperty(value = "别名",dataType = "String")
    @NotNull(message = "课程别名不能为空", groups = {GroupOne.class, GroupSix.class, GroupSeven.class})
    private String alias;//别名

    @ApiModelProperty(value = "年级-数据字典中对应的名称",dataType = "String")
    private String gradeId;//年级-数据字典中对应的名称

    @Min(value = 0)
    @Max(value = 99)
    @ApiModelProperty(value = "学分",dataType = "Integer")
    private Integer credit;//学分

    @Max(value = 3)
    @Min(value = 1)
    @ApiModelProperty(value = "重要性：1为非常重要，2为一般重要，3为一般",dataType = "Integer")
    private Integer importance;//重要性

    @ApiModelProperty(value = "场地类型-数据字典取",dataType = "String")
    private String buildingTypeId;//场地类型-数据字典取

    @ApiModelProperty(value = "课程类型-数据字典取",dataType = "String")
    private String typeId;//课程类型-数据字典取

    @ApiModelProperty(value = "课程类型名称 同课程名称说明",dataType = "String")
    private String typeName;//课程类型名称 同课程名称说明

    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;//学校id

    @ApiModelProperty(value = "批量添加课程id")
    @NotNull(message = "课程不能为空",groups = {GroupTwo.class})
    private String[] ddId;
    @ApiModelProperty(value = "批量添加课程名称")
    private String[] names;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空", groups = {GroupThree.class, Default.class})
    @Valid
    private Pager pager;

    @ApiModelProperty(value = "年级名称,取数据字典的名称",dataType = "String")
    private String gradeName;//年级名称
    @ApiModelProperty(value = "场地类型名称,取数据字典的名称",dataType = "String")
    private String buildingTypeName;//场地类型名称

    @ApiModelProperty(value = "课程考试时用到的总分",dataType = "Integer")
    @NotNull(message = "课程总分必传", groups = {GroupSix.class, GroupSeven.class})
    private Integer totalScore;
    @ApiModelProperty("试卷id")
    private String paperId;
    @ApiModelProperty("试卷标题")
    private String paperTitle;
    @ApiModelProperty("试卷贴图,给线下考试或者网阅考试时自己答题卡使用")
    private String[] paperImgs;
    @ApiModelProperty("答题卡")
    private AnswerSheet answerSheet;
    @ApiModelProperty("阅卷机上传数据后,本课程阅卷数据是否全部上传")
    private Boolean allUpload;
    @ApiModelProperty("该科目所有试卷所有题型都已阅卷完毕")
    private Boolean allReview;

    @ApiModelProperty("0为不可参与考试，1为可参与考试")
    private Integer exam;


}
