package com.yice.edu.cn.common.pojo.wb.classRegister;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupFive;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("上课记录表")
public class ClassRegister{

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("章节id")
    @NotNull(message = "章节id不能为空",groups = {GroupOne.class})
    private String chapterId;

    @ApiModelProperty("教师id")
    private String teacherId;

    @ApiModelProperty("学校id")
    private String schoolId;

    @ApiModelProperty("年级id")
    @NotNull(message = "年级id不能为空",groups = {GroupOne.class})
    private String gradeId;

    @ApiModelProperty("班级id")
    @NotNull(message = "班级id不能为空",groups = {GroupOne.class})
    private String classId;

    @ApiModelProperty("年级")
    private String gradeName;

    @ApiModelProperty("班级")
    private String classesName;

    @NotNull(message = "课堂名称不能为空",groups = {GroupOne.class})
    @ApiModelProperty("课堂名称")
    private String classroomName;

    @ApiModelProperty("0: 已下课 1：正在上课  2：继续上课")
    private Integer status;

    @ApiModelProperty("上课开始时间")
    private String startTime;

    @ApiModelProperty("上课结束时间")
    private String endTime;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("授课老师")
    private String name;

    @ApiModelProperty("上课章节")
    private String courseName;

    @ApiModelProperty("课程名称")
    private String subjectName;

    @ApiModelProperty("搜索关键字")
    private String keyWord;

    @ApiModelProperty("创建时间开始")
    private String createTime1;

    @ApiModelProperty("创建时间结束")
    private String createTime2;

    @ApiModelProperty("学期")
    private String term;

    @ApiModelProperty("学年")
    private String schoolYear;

    //分页排序等
    @Transient
    private Pager pager;
}
