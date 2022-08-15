package com.yice.edu.cn.common.pojo.jw.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
* 教师在班级信息，关联班级通过id获取年级和班级编号
 * 课程信息
*
*/
@Data
@Accessors(chain = true)
public class TeacherClasses{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "年级",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "班级（班号）",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String classesName;
    @ApiModelProperty(value = "班级对应的应届年份",dataType = "Integer")
    private Integer enrollYear;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "学段名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "课程id",dataType = "String")
    private String courseId;
    @ApiModelProperty(value = "课程别名",dataType = "String")
    private String courseName;
    @ApiModelProperty(value = "系统课程名",dataType = "String")
    private String subjectName;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;

    @Transient
    private List<TeacherClassesCourse> teacherClassesCourseList;

    @ApiModelProperty(value = "教师所教班级总人数",dataType = "String")
    @Transient
    private String stutotal;
}
