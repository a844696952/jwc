package com.yice.edu.cn.common.pojo.dm.school;


import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 学校事件表
 */
@ApiModel(description = "学校事件表")
@Data
public class DmSchoolBigNews{

    @ApiModelProperty(value = "学校事件主键id",dataType = "String")
    @NotNull(message = "id不能为空")
    private String id;//大事件编号

    @ApiModelProperty(value = "学校编号",dataType = "String")
    private String schooId;//学校编号

    @ApiModelProperty(value = "事件名称",dataType = "String")
    @NotNull(message = "事件名称不能为空")
    private String activeName;//大事件名称

    @ApiModelProperty(value = "事件封面图片的url地址",dataType = "String")
    @NotNull(message = "事件封面不能为空")
    private String imgUrl;//大事件封面

    @ApiModelProperty(value = "事件的内容",dataType = "String")
    @NotNull(message = "事件内容不能为空")
    private String description;//大事件内容

    @ApiModelProperty(value = "排序",dataType = "Integer")
    @NotNull(message = "排序不能为空")
    private Integer sort;//排序

    @ApiModelProperty(value = "当前这条数据的状态，用于逻辑删除",dataType = "Integer")
    private Integer newsStatus;//大事件状态

    @ApiModelProperty(value = "事件发生的时间",dataType = "String")
    @NotNull(message = "获取时间不能为空")
    private String haveTime;//获取时间

    @ApiModelProperty(value = "修改的时间",dataType = "String")
    private String updateTime;//修改时间

    @ApiModelProperty(value = "添加这条数据的时间",dataType = "String")
    private String createTime;//创建时间
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
