package com.yice.edu.cn.common.pojo.jy.wisdomclassroom;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 课堂检测实体对象
 */
@Document
@Data
public class ClassTest implements Serializable {

    @ApiModelProperty(value = "主键",dataType = "String")
    @Indexed
    private String id;
    @ApiModelProperty(value = "课程名称",dataType = "String")
    private String courseName;
    @ApiModelProperty(value = "课堂作业名称",dataType = "String")
    private String classWorkName;
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String className;
    @ApiModelProperty(value = "班级ID",dataType = "String")
    private String classId;

    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "学生总数",dataType = "Integer")
    private Integer studentCount;
    @ApiModelProperty(value = "上课时间",dataType = "String")
    private String attendClassTime;
    @ApiModelProperty(value = "习题详情")
    private List<TopicDetail> topicDetails;
    @ApiModelProperty(value = "易错题题号")
    private String fallibilityTopics;
    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    private String teacherId;


    /**
     * 业务字段
     */
    private String beginTime;
    private String endTime;
    private String searchKey;
    private String gradeId;

    //分页排序
    @Transient
    @NotNull
    private Pager pager;
}
