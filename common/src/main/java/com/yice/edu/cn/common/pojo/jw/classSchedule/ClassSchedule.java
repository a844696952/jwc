package com.yice.edu.cn.common.pojo.jw.classSchedule;

import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 *课程表（班级，老师）
 *
 */
@Data
@ExcelTarget("classSchedule")
@Document
public class ClassSchedule implements Serializable {

    private static final long serialVersionUID = -4121725449872794307L;

    @NotNull
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;//主键id

    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间

    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;//更新时间

    @ApiModelProperty(value = "删除标识",dataType = "String")
    private String del;//删除标识

    @Indexed
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;//年级id

    @Transient
    private String gradeName;//年级名称

    @Indexed
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;//班级id

    @Transient
    private String className;//班级名称

    @Indexed
    @Size(max = 7,min = 1)
    @ApiModelProperty(value = "周几对应的id（固定1-7）",dataType = "Integer")
    private Integer weekId;//周几对应的id（固定1-7）
    private String weekName;//周几名称（周一~周天）

    @Indexed
    @Size(max = 11,min = 1)
    @ApiModelProperty(value = "节数（第几节固定1开始）",dataType = "Integer")
    private Integer numberId;//节数（第几节固定1开始）

    private String numberName;//节数名称(第几节固定1开始)

    @Indexed
    @ApiModelProperty(value = "场地id",dataType = "String")
    private String spaceId;//场地id

    @Transient
    private String spaceName;//场所名称

    @ApiModelProperty(value = "记入最后一次操作",dataType = "String")
    private String courseTeacherId;//最后一次操作(CRUD)

    @Indexed
    @ApiModelProperty(value = "课程id",dataType = "String")
    private String courseId;//课程id

    @Transient
    private String courseName;//课程名称

    @Indexed
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;//教师id

    @Transient
    private String teacherName;//教师名称

    @Transient
    private String tearcherImgUrl;//教师头像url

    @Indexed
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;//学校id

    @Indexed
    @ApiModelProperty(value = "课程类型id",dataType = "String")
    private String typeId;//课程类型id

    @Indexed
    @ApiModelProperty(value = "教师班级课程id",dataType = "String")
    private String teacherClasssCourseId;//教师班级课程id

    @ExcelCollection(name = "星期")
    private List<Schedule> schedule;

    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
    private Integer courseTime;
    @Indexed
    private String academicBuildingId;//楼栋id
    private String academicBuildingName;//楼栋名称
    private Integer floor;//所在层数

    private String gradeNameClassName;//完整的班级名

    @Transient
    private long timestamp;//当前时间戳

    @ApiModelProperty("关联的课表列表id")
    private String scheduleId;

    @ApiModelProperty(value = "0-正常1-异常",dataType = "Integer")
    private Integer error;

    private List<ClassSchedule> classScheduleList;
}
