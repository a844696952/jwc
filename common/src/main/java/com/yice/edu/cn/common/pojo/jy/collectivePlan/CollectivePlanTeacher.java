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
*集体备课讨论组  与  教师关联表
*
*/
@Data
public class CollectivePlanTeacher{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "集体备课讨论组id",dataType = "String")
    private String collectivePlanId;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "教师名称",dataType = "String")
    private String teacherName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "教师id数组，以逗号隔开",dataType = "String")
    private String teacherIds;

    @ApiModelProperty(value = "讨论组教师id，逗号分隔",dataType = "String")
    private List<String> teacherIdss;

    @ApiModelProperty(value = "教师头像",dataType = "String")
    private String imgUrl;
}
