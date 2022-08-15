package com.yice.edu.cn.common.pojo.yedAdmin;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("app学校权限id")
public class AppSchoolPerm{
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("学校排序字段")
    private String schoolSort;
    @ApiModelProperty("我的应用模块是否展示->0表示隐藏，1表示不隐藏")
    private Integer flags;
    @ApiModelProperty("我的列表是否展示->0表示隐藏，1表示不隐藏")
    private Integer myListModule;
    @ApiModelProperty("属于哪个app,0:原生教师端,1:小程序教师端,2:原生家长端,3:小程序家长端")
    private Integer whatApp;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
