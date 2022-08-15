package com.yice.edu.cn.common.pojo.jy.resources;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*创建时间：2018-10-30。说明：用于存放我的资源的所有类型，相当于文件夹
*
*/
@Data
public class JyResoucesType{

    @ApiModelProperty(value = "资源类型编号",dataType = "String")
    private String id;
    @ApiModelProperty(value = "父编号",dataType = "String")
    private String parentId;
    @ApiModelProperty(value = "学校编号",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "讲师编号",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "文件名，长度10个字符限制",dataType = "String")
    private String name;
    @ApiModelProperty(value = "文件所属类型，0：个人资源，1：校本资源，2：我收藏的，3：我分享的",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private List<JyResoucesType> children;
}
