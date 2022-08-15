package com.yice.edu.cn.common.pojo.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;


@Data
@ApiModel("资源分类条件关联表")
public class ResourceCenterTypeCondition{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("资源中心id")
    private String resourceCenterId;
    @ApiModelProperty("分类id")
    private String typeId;
    @ApiModelProperty("分类名称")
    private String typeName;
    @ApiModelProperty("条件id")
    private String conditionId;
    @ApiModelProperty("条件名称")
    private String conditionName;
    @ApiModelProperty("学校id")
    private String schoolId;

    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
