package com.yice.edu.cn.common.pojo.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("往期课表记录表")
@Document
public class PastClassSchedule implements Serializable {

    private static final long serialVersionUID = 2085683063979581992L;
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("年级名称")
    private String gradeName;
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("周几 1-7对应周一到周日")
    private Integer weekId;
    @ApiModelProperty("第几节课 从1开始")
    private Integer numberId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("课表列表关联id")
    private String scheduleId;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("课表名称")
    private String courseName;
    //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private List<PastClassSchedule> classScheduleList;
}
