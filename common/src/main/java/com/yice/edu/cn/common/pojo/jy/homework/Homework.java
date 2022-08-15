package com.yice.edu.cn.common.pojo.jy.homework;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
*
*布置作业
*
*/
@Data
@Document
public class Homework extends CurSchoolYear {
    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    /*@ApiModelProperty(value = "seq_id",dataType = "String")
    @Indexed
    private String sqId;*/
    @ApiModelProperty(value = "教师id",dataType = "String")
    @Indexed
    private String teacherId;
    @ApiModelProperty(value = "老师名称",dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "老师头像",dataType = "String")
    private String teacherImg;
    @ApiModelProperty(value = "作业名称",dataType = "String")
    private String homeworkName;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "课程id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "课程名称",dataType = "String")
    private String subjectName;
    @ApiModelProperty(value = "截至时间",dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "作业内容(线下作业使用)",dataType = "String")
    private String homeworkContent;
    @ApiModelProperty(value = "图片数组(线下作业使用)",dataType = "String[]")
    private String[] imageArr;//图片数组(线下作业使用)
    @ApiModelProperty(value = "题目数组(线上作业使用)",dataType = "String[]")
    private Topics[] topicArr;//题目数组(线上作业使用)
    private List<TopicsCount> topicsCounts;//题目作答统计数组(线上作业使用)
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;//布置的班级对象
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String classesName;//布置的班级对象
    @ApiModelProperty(value = "应届年份",dataType = "String")
    private String enrollYear;
    /*@ApiModelProperty(value = "回复的方式(1.拍照上传 2.确认作业) 线下作业",dataType = "int")
    private Integer replyWay;//回复的方式(1.拍照上传 2.确认作业) 线下作业*/
    @ApiModelProperty(value = "发布状态(1.立即发布 2.暂不发布)",dataType = "int")
    private Integer publishStatus;//发布状态(1.立即发布 2.暂不发布)
    private String publishTime;//发布时间
    @ApiModelProperty(value = "作业类型(1.线上作业 2.线下作业)",dataType = "int")
    private Integer type;//作业类型(1.线上作业 2.线下作业)
    private String schoolId;//学校id
    private String createTime;//创建时间
    private Integer del;//是否删除(1.正常 2.删除)、
    @ApiModelProperty(value = "应提交人数",dataType = "Long")
    private Long shouldNum;//应提交人数
    @ApiModelProperty(value = "准时提交人数",dataType = "Long")
    private Integer punctualNum;//准时提交人数
    @ApiModelProperty(value = "逾期提交人数",dataType = "Long")
    private Integer overdueNum;//逾期提交人数
   /* //分页排序等
    @Transient
    @NotNull
    private Pager pager;*/
    @Transient
    private List<JwClasses> classesList;//布置的对象(班级数组)
    @Transient
    private String[] rangeTime;  //布置时间范围查
    private Double  knowledgenum;  //知识点数量
    private String knowledgeId;//知识点id

    @Transient
    private Map<String,Double> wrongTopicR;//布置的对象(班级数组)

    @Transient
    private String StudentName;//学生名字
    @Transient
    private Long  homeworkCount;  //该科目布置了几次作业
    @Transient
    private Double errorRate;//錯誤率
    @Transient
    private Double relativeQueC;//相关题数

    private String[] teacherIds; //班级教师数组
    private Topics[] wrrorTopicArr;//错题数组


    private Double contentNum;  //这道题目数量
    private String contentName;//这道题目题目


    private Double classesRightRate;  //班级这道题正确率(未完成+做错 /总人数)
    private Set<String> allStudentNames;//
    private String topicSqId;//具体莫道题的id
    private String studentId;//学生id
    private String typeId;//题目类型

    private List<HomeworkAnnexVo> homeworkAnnexList;//附件
    private List<HomeworkAudioVo> homeworkAudioList;//音频

    private  String ewbStartTime;//开始时间
    private String ewbEndTime;//结束时间

    private List<String> classNumList;
}
