package com.yice.edu.cn.common.pojo.jw.department;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*组织架构
*
*/
@Data
public class Department{
    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "name",dataType = "String")
    private String name;
    @ApiModelProperty(value = "schoolId",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "父id",dataType = "String")
    private String parentId;
    @ApiModelProperty(value = "路径",dataType = "String")
    private String path;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    private List<Department> children;
    //类型，0为组织架构，1为成员
    private int type;
    //头像路径
    private String imgUrl;
    //成员的手机号
    private String tel;
}
