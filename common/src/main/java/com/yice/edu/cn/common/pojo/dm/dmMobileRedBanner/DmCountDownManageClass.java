package com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("倒计时管理和班级关联表")
public class DmCountDownManageClass{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "倒计时id",dataType = "String")
    private String countDownId;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String className;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //年纪id
    private String gradeId;
}
