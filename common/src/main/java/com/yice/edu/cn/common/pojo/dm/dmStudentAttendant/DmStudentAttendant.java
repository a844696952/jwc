package com.yice.edu.cn.common.pojo.dm.dmStudentAttendant;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("值日生表")
public class DmStudentAttendant implements Cloneable{

    @ApiModelProperty("值日生编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    @ApiModelProperty("班级编号")
    private String classId;
    @ApiModelProperty("字典表星期几id")
    private String classCardId;
    @ApiModelProperty("字典表星期几id")
    private String ddId;
    @ApiModelProperty("学生id列表（多个学生，以逗号隔开）")
    private String studentIds;
    @ApiModelProperty("学生名称列表（多个，以逗号隔开）")
    private String studentNames;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;

    @ApiModelProperty("星期几")
    private String weekName;

    @Transient
    private String[] studentList;

    private List<StudentClazz> stuList;
    @Data
    public static class StudentClazz{
        private String studentId;
        private String studentName;

        public StudentClazz(String studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }

        public StudentClazz() {
        }
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
