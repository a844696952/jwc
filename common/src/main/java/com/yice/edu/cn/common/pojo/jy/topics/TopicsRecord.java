package com.yice.edu.cn.common.pojo.jy.topics;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*学生答题记录表
*
*/
@Data
@Document
public class TopicsRecord extends CurSchoolYear {

    private String id;
    //private String sqId;//sq_id
    @ApiModelProperty(value = "题目对象",dataType = "Topics")
    private Topics topicsObj;//题目对象
    @ApiModelProperty(value = "渠道类型(1.作业)",dataType = "int")
    private Integer channelType;//渠道类型(1.作业)
    @ApiModelProperty(value = "来源Id",dataType = "String")
    private String channelId;//来源Id(对应homework中的sqId)
    @Indexed
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;//学生id
    @ApiModelProperty(value = "学生名称",dataType = "String")
    private String studentName;//学生名称
    private Student student;//学生详细对象
    @ApiModelProperty(value = "学生答案",dataType = "String")
    private String answer;//学生答案
    private Integer correct;//是否正确(1.正确 2.错误 3.未作答)
    private String answerTime;//答题时间
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;//年级id
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;//年级名称
    @ApiModelProperty(value = "课程id",dataType = "String")
    private String subjectId;//课程id
    @ApiModelProperty(value = "课程名称",dataType = "String")
    private String subjectName;//课程名称
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;//布置的班级对象
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String classesName;//布置的班级对象
    @ApiModelProperty(value = "班级应届年份",dataType = "String")
    private String enrollYear;//布置的班级对象
    private String del;//删除标识
   /* //分页排序等
    @Transient
    @NotNull
    private Pager pager;*/

    @Transient
    private String[] rangeTime;  //布置时间范围查

    @Transient
    private String schoolId;//学校id

    @Transient
    private List<String> studentIds;

    //---错题分析用到的字段
    private Double trueCount;//知识点正确的数量
    private Double wrongCount;//知识点错误的数量
    private String knowledgeId;//知识点的id
    private String knowledgeName;//知识点的名字
    private Double totalCount;//知识点总数
    private Double divideCount;//知识点的错误率
    private Double relativeTc;//相关题数

    private Double wrongTopicCount;//错题本数
    private Integer seatNumber;//座位号
    private Double wkCount;//薄弱知识点数
    private Double wknowledgeC;
}
