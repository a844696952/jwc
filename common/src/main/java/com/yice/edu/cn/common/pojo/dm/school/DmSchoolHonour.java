package com.yice.edu.cn.common.pojo.dm.school;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
/**
*
*学校荣誉表
*
*/
@ApiModel(description = "学校荣誉表")
@Data
public class DmSchoolHonour{

    @ApiModelProperty(value = "学校荣誉主键id",dataType = "String")
    @NotNull(message = "id不能为空")
    private String id;//荣誉编号

    @ApiModelProperty(value = "学校编号",dataType = "String")
    private String schooId;//学校编号

    @ApiModelProperty(value = "学校荣誉名称",dataType = "String")
    @NotNull(message = "荣誉名称不能为空")
    private String activeName;//荣誉名称

    @ApiModelProperty(value = "荣誉的封面图片的url",dataType = "String")
    @NotNull(message = "荣誉封面不能为空")
    private String imgUrl;//荣誉封面

    @ApiModelProperty(value = "荣誉的内容",dataType = "String")
    @NotNull(message = "荣誉内容不能为空")
    private String description;//荣誉内容

    @ApiModelProperty(value = "排序",dataType = "Integer")
    @NotNull(message = "排序不能为空")
    private Integer sort;//排序

    @ApiModelProperty(value = "当前这条数据的状态，用于逻辑删除",dataType = "Integer")
    private Integer honourStatus;//荣誉状态

    @ApiModelProperty(value = "荣誉获得的时间",dataType = "String")
    @NotNull(message = "发生时间不能为空")
    private String haveTime;//获取时间

    @ApiModelProperty(value = "修改的时间",dataType = "String")
    private String updateTime;//修改时间

    @ApiModelProperty(value = "数据添加的时间",dataType = "String")
    private String createTime;//创建时间
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
