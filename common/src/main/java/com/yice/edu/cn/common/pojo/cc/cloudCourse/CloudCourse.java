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
@ApiModel("云课程")
public class CloudCourse{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("课程名称")
    private String name;
    @ApiModelProperty("提前进入课堂时间")
    private Integer advanceEntryTime;
    @ApiModelProperty("上课时间")
    private String startTime;
    @ApiModelProperty("下课时间")
    private String endTime;
    @ApiModelProperty("提醒时间")
    private Integer alertTime;
    @ApiModelProperty("能否旁听")
    private Boolean allowListen;
    @ApiModelProperty("创建课程教师")
    private Teacher createTeacher;
    @ApiModelProperty("听课人数")
    private Long teacherCount;
    @ApiModelProperty("课程数量")
    private Long courseCount;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("上课时长")
    private String duration;
    @ApiModelProperty("校内听课教师列表")
    private List<ListenTeacher> listenTeachers;
    @ApiModelProperty("校外听课账号列表")
    private List<OtherSchoolAccount> otherSchoolAccounts;
    @ApiModelProperty("开课学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty("直播推流地址")
    private String livePushPath;

    @Transient
    @ApiModelProperty("已完成课程")
    private String completedCourse;

    @Transient
    @ApiModelProperty("课程视频数量")
    private Integer courseVideosQuantity;
    @Transient
    @ApiModelProperty("时间区间")
    private String[] rangeArray;

    @Transient
    @ApiModelProperty("创建人姓名")
    private String createName;

    @Transient
    @ApiModelProperty("子课程列表")
    private List<CloudSubCourse> cloudSubCourseList;
    @Transient
    @ApiModelProperty("子课程列表")
    List<String>cloudCourseIdList;

    @Transient
    @ApiModelProperty("是否可删除")
    private boolean deletabled;

}
