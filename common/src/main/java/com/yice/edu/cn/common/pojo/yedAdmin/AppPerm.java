package com.yice.edu.cn.common.pojo.yedAdmin;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("app端菜单权限")
public class AppPerm{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校权限id")
    private String schoolPermId;
    @ApiModelProperty("排序，从小到大")
    private Integer sort;
    @ApiModelProperty("app端显示的名称")
    private String appName;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("分组名称")
    private String groupName;
    @NotNull(message = "访问来源不能为空，0:原生教师端,1:小程序教师端,2:原生家长端,3:小程序家长端",groups = {GroupOne.class})
    @Valid
    @ApiModelProperty("属于哪个app,0:原生教师端,1:小程序教师端,2:原生家长端,3:小程序家长端")
    private Integer whatApp;
    @ApiModelProperty("app图标")
    private String appIcon;
    @ApiModelProperty("唯一标识符")
    private String identify;
    //分页排序等
    @Transient
    private Pager pager;
    private List<AppPerm> children;
    private String schoolId;
    private String teacherId;
    @ApiModelProperty("对应学校的排序字段")
    private Integer schoolSort;
    @ApiModelProperty("我的应用模块是否展示->0表示隐藏，1表示不隐藏")
    private Integer flags;
    @ApiModelProperty("我的列表展示模块->0表示隐藏，1表示不隐藏")
    private Integer myListModule;
}
