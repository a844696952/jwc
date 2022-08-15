package com.yice.edu.cn.common.pojo.jy.collectivePlan;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*集体备课讨论组  与 教案关联表
*
*/
@Data
public class TeacherCollection{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "教案id",dataType = "String")
    private String teacherPlanId;
    @ApiModelProperty(value = "讨论组id",dataType = "String")
    private String collectionPlanId;
    @ApiModelProperty(value = "提交时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "修改次数",dataType = "Integer")
    private Integer updateCount;
    @ApiModelProperty(value = "评论次数",dataType = "Integer")
    private Integer commentCount;
    @ApiModelProperty(value = "0:讨论通过  1：正在讨论",dataType = "Integer")
    private Integer status;
    @ApiModelProperty(value = "老师id",dataType = "String")
    private String teacherId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "教案ids,以逗号分隔",dataType = "String")
    private String teacherPlanIds;

    @ApiModelProperty(value = "教案ids，逗号分隔",dataType = "String")
    private List<String> teacherPlanIdss;
}
