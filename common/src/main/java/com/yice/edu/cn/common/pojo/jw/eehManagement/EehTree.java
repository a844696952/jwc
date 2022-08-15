package com.yice.edu.cn.common.pojo.jw.eehManagement;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("")
public class EehTree{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("父级id")
    private String parentId;
    @ApiModelProperty("类型(1:电教馆2:教育局)")
    private String type;
    @ApiModelProperty("省份id")
    private String provinceId;
    @ApiModelProperty("市id")
    private String cityId;
    @ApiModelProperty("区id")
    private String areaId;
    @ApiModelProperty("省份名称")
    private String provinceName;
    @ApiModelProperty("市名称")
    private String cityName;
    @ApiModelProperty("区名称")
    private String areaName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("树级别")
    private Integer level;
    @ApiModelProperty("是否是叶子节点(1是 2.否）")
    private Integer leaf;
    @ApiModelProperty("状态(1:启用 2:禁用)")
    private String status;
    @ApiModelProperty("关系（子列表）")
    private String relation;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //额外字段
    private List<EehTree> children;
    private String accountStatus;
}
