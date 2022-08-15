package com.yice.edu.cn.common.pojo.dm.kq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 考勤统计实体对象
 */
@Data
@Document
public class EccKqStatistics {


    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;

    @ApiModelProperty(value = "学生ID", dataType = "String")
    private String studentId;

    @ApiModelProperty(value = "班级ID", dataType = "String")
    private String classId;

    @ApiModelProperty(value = "学校ID", dataType = "String")
    private String schoolId;

    @ApiModelProperty(value = "学生名称", dataType = "String")
    private String studentName;

    @ApiModelProperty(value = "年级ID", dataType = "String")
    private String gradeId;

    @ApiModelProperty(value = "人脸ID", dataType = "String")
    private String faceId;

    @ApiModelProperty(value = "请假开始时间", dataType = "String")
    private String leaveBeginTime;

    @ApiModelProperty(value = "请假结束时间", dataType = "String")
    private String leaveEndTime;

    @ApiModelProperty(value = "开启状态 0-默认状态 1-未到 2--迟到 3--已到 4--请假")
    private Integer kqState;

    @ApiModelProperty(value = "是否是异常数据 0--是  1 不是")
    private Integer aberrant;

    @ApiModelProperty(value = "打卡时间", dataType = "String")
    private String dKTime;


}
