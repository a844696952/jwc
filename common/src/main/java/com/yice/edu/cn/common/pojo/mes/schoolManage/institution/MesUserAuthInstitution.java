package com.yice.edu.cn.common.pojo.mes.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("用户制度权限表")
public class MesUserAuthInstitution{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("制度ID")
    private String institutionId;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("用户类型 0--老师 1--学生")
    private Integer userType;
    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("父制度id")
    private String institutionParentId;
    @ApiModelProperty("等级 1- 一级制度 2--二级制度")
    private Integer level;
    @ApiModelProperty("1-存在组织架构树中 0--不存在")
    private Integer isExist;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
