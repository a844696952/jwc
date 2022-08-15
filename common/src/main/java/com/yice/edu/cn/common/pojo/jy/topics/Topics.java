package com.yice.edu.cn.common.pojo.jy.topics;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.TopicPosition;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudentAnalyseTopicOption;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@Document
@Accessors(chain = true)
public class Topics {
    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;//m主键
    /*@Indexed
    private String sqId;//废弃*/
    @Indexed
    @ApiModelProperty(value = "学段id",dataType = "String")
    private String annualPeriodId;//学段id
    @ApiModelProperty(value = "学段名称",dataType = "String")
    private String annualPeriodName;//学段名称
    @Indexed
    @ApiModelProperty(value = "题型id [1:判断，2:单选，3:多选，4:填空题，5:问答题, 6:作文题]",dataType = "int")
    private Integer typeId;//题型id [1:判断，2:单选，3:多选，4:填空题，5:问答题, 6:作文题]
    @ApiModelProperty(value = "题型",dataType = "String")
    private String typeName;//题型
    @ApiModelProperty(value = "题目内容",dataType = "String")
    private String content;//题目内容
    @ApiModelProperty(value = "题目内容-纯文本内容",dataType = "String")
    private String contentText;//题目内容
    @ApiModelProperty(value = "选项数量",dataType = "String")
    private String optionNum;//选项数量
    @ApiModelProperty(value = "答案",dataType = "String")
    private String[] answer;//答案
    @ApiModelProperty(value = "解析",dataType = "String")
    private String analysis;//解析
    @Indexed
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;//年级id
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;//年级名称
    @Indexed
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;//科目id
    @ApiModelProperty(value = "科目名称",dataType = "String")
    private String subjectName;//科目名称
    private String updateTime;//更新时间
    private String createTime;//创建时间
    private String createUser;//创建人
    private String[] options;//选项
    @Indexed
    @ApiModelProperty(value = "知识点",dataType = "List")
    private List<KnowledgePoint> knowledges;//知识点列表
    @ApiModelProperty(value = "题目难度",dataType = "Integer")
    private String difficult;//题目难度
    private String difficultName;//题目难度
    @ApiModelProperty(value = "题类",dataType = "String")
    private String[] topicClass;//题类
    @ApiModelProperty(value = "题类",dataType = "String")
    private String topicClassName;//题类
    @ApiModelProperty(value = "章节id",dataType = "List")
    private List<String> categories;//章节id
    @ApiModelProperty(value = "章节",dataType = "String")
    private String categoryName;//章节
    @ApiModelProperty(value = "题目类型id",dataType = "String")
    private String questionItemId;//题目类型id
    @ApiModelProperty(value = "题目类型",dataType = "String")
    private String questionItemName;//题目类型
    @ApiModelProperty(value = "问题数量",dataType = "String")
    private String questionNum;//问题数量
    @ApiModelProperty(value = "子类",dataType = "List")
    private List<Topics> child;
    @Transient
    private Integer[] typeIds;//多个题目类型 给查询用
    @Transient
    private String errorCode;//异常返回编码

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    @Transient
    private Integer answerStudentCount; //本道题学生做对人数
    @Transient
    private String classesScore; //班级得分率
    @Transient
    private String studentAnswer; //记录学生答案
    @Transient
    private Integer correct;//是否正确(1.正确 2.错误 3.未作答)
    @Transient
    private Integer topicNumber; //每次作业的题号
    @Transient
    private Integer notFinishedStudentNumber;//未完成人数
    private Integer errorStudentNumber;//答错人数
    private Integer allStudentNumber;//总人数
    private Double classesErrorRate;  //班级这道题错误率(未完成+做错 /总人数)
    private List<String> homeworkAnalyseStudentName;//作业分析每道题的学生名字
    private List<String> homeworkAnalyseStudentAnswer;//作业分析每道题的学生答案

    private Map<String,Double> allTitleMap;//每道题，正确率
    private Map<String,List<Student>> studentTitleMap;//每道题，学生名单
    private List<HomeworkStudentAnalyseTopicOption> homeworkStudentAnalyseTopicOption; //作业分析每道题的每个选项的学生
    private Double classRigtRate;//这道题的班级得分率

    private String img;//app端使用->小题图片
    private String youAnswer;//app端使用->你的答案
    private TopicPosition[] topicPositions;//app端使用->题目坐标
}
