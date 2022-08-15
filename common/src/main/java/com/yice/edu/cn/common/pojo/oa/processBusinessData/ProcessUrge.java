package com.yice.edu.cn.common.pojo.oa.processBusinessData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 催办实例类
 */
@Document
@Data
public class ProcessUrge {
    @ApiModelProperty(value = "流程催办id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "流程实例id",dataType = "String")
    @Indexed
    private String processBusinessId;
    @ApiModelProperty(value = "流程库的id",dataType = "String")
    @Indexed
    private String processLibId;
    @Indexed
    @ApiModelProperty(value = "通知催办的日期",dataType = "String")
    private String urgeDate;
    @ApiModelProperty(value = "通知催办的老师Id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "通知催办的老师名称",dataType = "String")
    private String sendTeacherName;
    @ApiModelProperty(value = "通知催办的老师手机号",dataType = "String")
    private String sendTeacherTel;
    @ApiModelProperty(value = "发起时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;


}
