package com.yice.edu.cn.common.pojo.jw.adminPerm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import java.util.List;

@Data
@ApiModel("校管理员权限")
public class AdminPerm {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("权限名称")
    private String title;
    @ApiModelProperty("唯一标识符")
    private String identify;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("0tab菜单,1打开新浏览器,2代理打开浏览器")
    private Integer type;
    @ApiModelProperty("路由名称,可为空")
    private String routeName;
    @ApiModelProperty("除掉host外的绝对路径")
    private String urlPath;
    @ApiModelProperty("排序字段")
    private Integer sortNum;
    @ApiModelProperty("从学校总权限选取的菜单")
    private String schoolPermId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private List<AdminPerm> children;
}
