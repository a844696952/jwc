package com.yice.edu.cn.common.pojo.jw.timetable;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.timetable.GA.TeachInfoSplit;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@NoArgsConstructor
@ApiModel("课表")
public class Timetable{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "jobId",dataType = "String")
    private String jobId;
    @ApiModelProperty(value = "本周第几天",dataType = "Integer")
    private Integer weekdays;
    @ApiModelProperty(value = "当天的时间片位置",dataType = "Integer")
    private Integer timeSlot;
    @ApiModelProperty(value = "整个课程表索引>0",dataType = "Integer")
    private Integer slot;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "gradeId",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "classId",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "className",dataType = "String")
    private String className;
    @ApiModelProperty(value = "teacherId",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "teacherName",dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "courseId",dataType = "String")
    private String courseId;
    @ApiModelProperty(value = "courseName",dataType = "String")
    private String courseName;
    @ApiModelProperty(value = "createTime",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    private Pager pager;
    
    public void setTimetable(TeachInfoSplit teachingPlanSplit) {
    	
    	this.classId=teachingPlanSplit.getClassId();
		this.className=teachingPlanSplit.getClassName();
		this.teacherId=teachingPlanSplit.getTeacherId();
		this.teacherName=teachingPlanSplit.getTeacherName();
		this.courseId=teachingPlanSplit.getJwCourseId();
		this.courseName=teachingPlanSplit.getName();
    	
    }

	public Timetable( Timetable t) {
		this.id = t.id;
		this.jobId = t.jobId;
		this.weekdays = t.weekdays;
		this.timeSlot = t.timeSlot;
		this.slot = t.slot;
		this.schoolId = t.schoolId;
		this.gradeId = t.gradeId;
		this.classId = t.classId;
		this.className = t.className;
		this.courseId = t.courseId;
		this.courseName = t.courseName;
		this.createTime = t.createTime;
	}
    
    
    
}
