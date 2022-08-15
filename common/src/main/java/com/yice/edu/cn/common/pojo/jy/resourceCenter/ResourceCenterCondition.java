package com.yice.edu.cn.common.pojo.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("资源中心:条件表")
public class ResourceCenterCondition{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("条件名称")
    private String name;
    @ApiModelProperty("权重")
    private Integer sort;
    @ApiModelProperty("分类id")
    private String typeId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
