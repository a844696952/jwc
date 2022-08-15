package com.yice.edu.cn.common.pojo.jw.qusSurvey;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.ArrayList;

/**
*
*提交整个问卷记录的表
*{"surveyId":"1129267984574058496",
 * "studentId":"1120600131356463104",
 * "classId":"2424383007141748736",
 * "teacherId":"1117699408566292480",
 * "qusSurveyOptionList":[
 * {"optionScore":"20","questionId":"1129268264325746688","optionId":"1129268264325746690"},
 * {"optionScore":"10","questionId":"1129268264325746692","optionId":"1129268264325746693"}
 * ]，
 * "questionAnswer":"老师很好"
 *
 *
 * }
*/
@Data
@Document
public class QusSurveySubmit{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "问卷id",dataType = "String")
    @Indexed
    private String surveyId;
    @ApiModelProperty(value = "老师id",dataType = "String")
    @Indexed
    private String teacherId;
    @ApiModelProperty(value = "班级id",dataType = "String")
    @Indexed
    private String classId;
    @ApiModelProperty(value = "学生Id",dataType = "String")
    private String studentId;
    @ApiModelProperty(value = "学生分数",dataType = "String")
    private Integer studentScore;
    @ApiModelProperty(value = "问答题答案",dataType = "String")
    private String questionAnswer;
    @ApiModelProperty(value = "选项集合",dataType = "String")
    private ArrayList<QusSurveyOption> qusSurveyOptionList;
    @ApiModelProperty("发布时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("schoolId")
    private String schoolId;

    @ApiModelProperty(value = "被评老师id",dataType = "String")
    private String beEvaluatedTeacherId;
    @ApiModelProperty(value = "被评老师姓名",dataType = "String")
    private String beEvaluatedTeacherName;
    @ApiModelProperty(value = "评审老师id",dataType = "String")
    private String evaluateTeacherId;
    @ApiModelProperty(value = "评审老师姓名",dataType = "String")
    private String evaluateTeacherName;
    @ApiModelProperty(value = "分数",dataType = "String")
    private Integer score;
    @ApiModelProperty("提交状态:1、未提交  2、已提交")
    private String submitStatus;
    @ApiModelProperty(value = "整体平均分",dataType = "String")
    private Double topAvg;
    @ApiModelProperty(value = "整体最高分",dataType = "String")
    private Integer topHighScore;
    @ApiModelProperty(value = "整体最低分",dataType = "String")
    private Integer topLowScore;
     //分页排序等

    @Transient
    @Valid
    private Pager pager;
}
