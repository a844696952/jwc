package com.yice.edu.cn.common.pojo.xw.dj;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("我的学习，教师")
public class XwDjMyStudyTeacher {

    @ApiModelProperty(value = "主键id", dataType = "String")
    private String id;
    @ApiModelProperty(value = "学习资源id", dataType = "String")
    private String studyResourceId;
    @ApiModelProperty(value = "教师id", dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "学校id", dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "是否学习状态（0未学习，1已学习）", dataType = "Integer")
    private Integer state;
    @ApiModelProperty(value = "完成学习时间", dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "接收到学习资源的时间", dataType = "String")
    private String receiveTime;
    @ApiModelProperty(value = "学习资源类型", dataType = "String")
    private String studyType;
    @ApiModelProperty(value = "学习资源标题", dataType = "String")
    private String studyTitle;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //学习资源
    private XwDjStudyResource xwDjStudyResource;
    //类型名称
    private String typeName;
    //教师头像
    private String imgUrl;
    //教师名称
    private String name;

    //学习开始时间，做时间区间查询
    private String startTime;
}
