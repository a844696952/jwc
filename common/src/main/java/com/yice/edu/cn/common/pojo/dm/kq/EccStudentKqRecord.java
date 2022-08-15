package com.yice.edu.cn.common.pojo.dm.kq;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.validateClass.GroupFour;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@ApiModel("云班牌学生考勤记录")
public class EccStudentKqRecord extends CurSchoolYear {
    private String id;
    private String schoolId;
    private String studentId;
    private String gradeId;
    private String classesId;
    private DayOfWeek dayOfWeek;
    private String createTime;
    private String dkTime;
    private ProtectedStudent student;
    @ApiModelProperty("打卡状态(1 缺卡 2 迟到 3 早退 4无需打卡 5正常 非5异常)")
    private String kqStatus;
    @ApiModelProperty(value = "打卡开始时间")
    private String dkBeginTime;
    @ApiModelProperty(value = "打卡结束时间")
    private String dkEndTime;
    private String gradeName;
    private String className;
    private String studentName;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空",groups = {GroupThree.class, GroupFour.class})
    @Valid
    private Pager pager;

    public EccStudentKqRecord() {
    }

    /**
     * 通过学生创建一个此刻的打卡记录
     * @param student
     */
    public EccStudentKqRecord(ProtectedStudent student){
        String now = DateUtil.now();
        this.schoolId = student.getSchoolId();
        this.classesId = student.getClassId();
        this.createTime = now;
        this.dayOfWeek = LocalDate.now().getDayOfWeek();
        this.dkTime = now;
        this.gradeId = student.getGradeId();
        this.student = student;
        this.studentId = student.getStudentId();
    }

}
