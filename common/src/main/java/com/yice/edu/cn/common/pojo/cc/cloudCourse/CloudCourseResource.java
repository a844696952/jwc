package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.cc.otherSchoolAccount.OtherSchoolAccount;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("云课堂上课信息资源")
public class CloudCourseResource{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("外键云课堂id")
    private String cloudCourseId;
    @ApiModelProperty("创建人教师")
    private Teacher createTeacher;
    @ApiModelProperty("授课教师")
    private Teacher teacher;
    @ApiModelProperty("课程名称")
    private String cloudCourseName;
    @ApiModelProperty("上课时间")
    private String beginTime;
    @ApiModelProperty("上课时长(分钟)")
    private Integer courseTime;
    @ApiModelProperty("当时校内听课教师列表")
    private List<ListenTeacher> listenTeachers;
    @ApiModelProperty("当时校外听课账号列表")
    private List<OtherSchoolAccount> otherSchoolAccounts;
    @ApiModelProperty("上传时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    
    //额外字段
    @Transient
    @ApiModelProperty("课程视频数")
    private Long count;

    @Transient
    @ApiModelProperty("已完成课程")
    private String completedCourse;

    @Transient
    @ApiModelProperty("子课程列表")
    private List<CloudSubCourse> cloudSubCourseList;

    @Transient
    @ApiModelProperty("上课时长(小时分钟)")
    private String courseHour;
}
