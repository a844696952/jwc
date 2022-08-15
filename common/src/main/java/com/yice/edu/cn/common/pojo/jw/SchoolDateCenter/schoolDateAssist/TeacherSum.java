package com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist;

import com.google.common.math.LongMath;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.DatePaper;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Api("教师总数")
@Data
public class TeacherSum {
    @ApiModelProperty("教师总数")
    private Long teacherNumber;
    @ApiModelProperty("教师分布访问情况")
    private List<JySchoolResourcesByDay> teacherVisit;
    @ApiModelProperty("纵坐标初始值")
    private Long initNumber;
    @ApiModelProperty("日访问量")
    private Long dailyVisits;
    @ApiModelProperty("男老师数量")
    private Long boyTeacherNumber;
    @ApiModelProperty("女老师数量")
    private Long girlTeacherNumber;
    @ApiModelProperty("教师科目和对应人数")
    private List<Map<String,Object>> teacherList;
    @ApiModelProperty("男教师占比")
    private String rateOfBoy;
    @ApiModelProperty("女教师占比")
    private String rateOfGirl;
    @ApiModelProperty("教师年龄段人数")
    private List<TeacherAge> teacherAges;
    @ApiModelProperty("存储的值")
    private List<Long> halfMonth;
    @ApiModelProperty("学校id")
    private String schoolId;
}
