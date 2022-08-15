package com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel(" 资产类型，v0.0.02")
public class AssetsCategory{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("类型名称")
    private String assetsCategoryName;
    @ApiModelProperty("资产属性")
    private String assetsProperty;
    @ApiModelProperty("类型父ID")
    private String parentId;
    @ApiModelProperty("所有祖先ID，包括自己")
    private String ancestorIds;
    @ApiModelProperty("删除标准 del 1:2(正常：删除)")
    private Integer del;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建人名称")
    private String createUsername;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    private List<AssetsCategory> children;
}
