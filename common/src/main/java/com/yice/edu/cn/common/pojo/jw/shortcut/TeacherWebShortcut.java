package com.yice.edu.cn.common.pojo.jw.shortcut;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("用户首页快捷应用")
public class TeacherWebShortcut{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("权限id")
    private String permId;
    @ApiModelProperty("权限名称")
    private String title;
    @ApiModelProperty("唯一标识符")
    private String identify;
    @ApiModelProperty("分组id")
    private String groupId;
    @ApiModelProperty("分组名称")
    private String groupName;
    @ApiModelProperty("除掉host外的绝对路径")
    private String urlPath;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("web首页快捷图片")
    private String webIcon;
    @ApiModelProperty("路由名称")
    private String routeName;

    @ApiModelProperty(value = "应用类型，1、系统；2、第三方")
    private Integer type;
    @ApiModelProperty("过期时间")
    private String expireDate;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
