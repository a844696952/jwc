package com.yice.edu.cn.common.pojo.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
public class JyPrepareLessonsDiscussReply{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "教案id",dataType = "String")
    private String teachingPlanId;
    @ApiModelProperty(value = "评论id",dataType = "String")
    private String discussId;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "回复",dataType = "String")
    private String reply;
    @ApiModelProperty(value = "回复时间",dataType = "String")
    private String replyTime;
    @ApiModelProperty(value = "教师头像",dataType = "String")
    private String teacherIcon;
    @ApiModelProperty(value = "教师名称",dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "教案组id",dataType = "String")
    private String usenetId;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
