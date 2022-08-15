package com.yice.edu.cn.common.pojo.jw.auth;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*学校总权限
*
*/
@Data
public class SchoolPerm{

    @ApiModelProperty(value = "id",dataType = "String")
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
    @ApiModelProperty(value = "app图标路径",dataType = "String")
    private String appIcon;
    @ApiModelProperty("是否完成，0未完成,1完成")
    private Integer finished;
    @ApiModelProperty("排序字段")
    private Integer sortNum;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    @Transient
    private Boolean autoBtn;
    @Transient
    private Boolean checked;
    //额外字段
    private List<SchoolPerm> children;
}
