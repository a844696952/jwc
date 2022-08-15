package com.yice.edu.cn.common.pojo.jw.auth;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*学校权限
*
*/
@Data
public class Perm {
    @ApiModelProperty(value = "和school_id联合唯一",dataType = "String")
    private String id;
    @ApiModelProperty(value = "权限名称",dataType = "String")
    private String title;
    @ApiModelProperty(value = "唯一标识符",dataType = "String")
    private String identify;
    @ApiModelProperty(value = "图标",dataType = "String")
    private String icon;
    @ApiModelProperty(value = "父id",dataType = "String")
    private String parentId;
    @ApiModelProperty(value = "0tab菜单,1打开新浏览器,2代理打开浏览器",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "路由名称,可为空",dataType = "String")
    private String routeName;
    @ApiModelProperty(value = "除掉host外的绝对路径",dataType = "String")
    private String urlPath;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "app图标路径",dataType = "String")
    private String appIcon;
    @ApiModelProperty("排序字段")
    private Integer sortNum;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外
    private List<Perm> children;
    private Boolean hasChild;
}
