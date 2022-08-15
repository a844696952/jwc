package com.yice.edu.cn.common.pojo.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("资源中心：分类表")
public class ResourceCenterType{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("权重")
    private Integer sort;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //额外字段
    private String code;//错误码
    private String msg;//错误信息
    private String [] ids;//条件删除额外字段
    private long num;//条件数量
    private List<ResourceCenterCondition> child;
}
