package com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("德育小程序职务权限表")
public class MesAppletsPostPerm{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("职务类型ID(参考字典表ad_dd type_id=9的类型)")
    private String postId;
    @ApiModelProperty("德育功能权限（新增到字典表中）")
    private String mesPermId;
    @ApiModelProperty("学习id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    private List<String> mesPermIds;
    /**
     * 职务名
     */
    @Transient
    private String postName;
    /**
     * 德育功能权限名
     */
    @Transient
    private String mesPerm;
    /**
     * 德育功能权限名（多个逗号隔开）
     */
    @Transient
    private String mesPerms;
}
