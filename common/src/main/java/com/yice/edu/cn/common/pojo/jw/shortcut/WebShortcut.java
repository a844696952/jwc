package com.yice.edu.cn.common.pojo.jw.shortcut;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("校园平台首页快捷应用")
public class WebShortcut{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("权限id")
    private String permId;
    @ApiModelProperty("快捷名称")
    private String shotName;
    @ApiModelProperty("权限名称")
    private String title;
    @ApiModelProperty("唯一标识符")
    private String identify;
    @ApiModelProperty("分组父类名称")
    private String groupName;
    @ApiModelProperty("分组父类id")
    private String groupId;
    @ApiModelProperty("除掉host外的绝对路径")
    private String urlPath;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("web首页快捷图片")
    private String webIcon;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("路由名称")
    private String routeName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
