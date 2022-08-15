package com.yice.edu.cn.common.pojo.dm.modeManage;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 考试信息实体对象
 */
@Document
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@ApiModel("考试模式实体对象")
public class ExamMode {

    @Id
    @Indexed
    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;

    @ApiModelProperty(value = "考试名称",dataType = "String")
    private String examName;

    @ApiModelProperty(value = "年级ID",dataType = "String")
    private String gradeId;

    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;

    @ApiModelProperty(value = "展示班牌数量",dataType = "Integer")
    private Integer classCount;

    @ApiModelProperty(value = "科目数量",dataType = "Integer")
    private Integer subjectCount;

    @ApiModelProperty(value = "监考老师总数",dataType="Integer")
    private Integer invigilatorCount;

    @ApiModelProperty(value = "学生总数",dataType = "Integer")
    private Integer studentCount;

    @ApiModelProperty(value = "Excel文件名",dataType="String")
    private String fileName;

    @ApiModelProperty(value = "考试信息 1--每场考试前 2--第一场开始前",dataType="Integer")
    private Integer showMode;

    @ApiModelProperty(value = "考试开始时间(第一场考试时间)",dataType="String")
    private String examBeginTime;

    private String examEndTime;

    @ApiModelProperty(value = "考试结束时间(最后一场考试时间)",dataType="String")
    private Integer beforeMinute;

    @ApiModelProperty(value = "推后时间",dataType="Integer")
    private Integer afterMinute;

    @ApiModelProperty(value = "状态 0--展示中 1-- 待展示 2--已结束",dataType = "Integer")
    private Integer status;


    @ApiModelProperty(value = "考试人员详情")
    private List<ExamInfo> examInfo;


    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;

    @ApiModelProperty(value = "创建日期",dataType = "String")
    private String createDate;

    @ApiModelProperty(value = "创建日期",dataType = "String")
    private String createTime;

    @ApiModelProperty("任务ID")
    private String taskId;



    //分页排序
    @Transient
    @NotNull
    private Pager pager;

    @Transient
    private long timestamp;

    @ApiModelProperty(value = "年纪集合")
    private List<JwClasses> classes;

    @ApiModelProperty(value = "考试信息集合")
    private List<ExamSubject> subjectList;

}
