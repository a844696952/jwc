package com.yice.edu.cn.common.pojo.jw.department;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
*
*组织架构
*
*/
@Data
public class DepartmentForSchoolNotify {
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
    @ApiModelProperty(value = "班号",dataType = "String")
    private Integer classNumber;

    @ApiModelProperty("班级总人数 ")
    private Integer classNum;
    @ApiModelProperty("班级读取人数")
    private Integer classReadNum;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    private List<DepartmentForSchoolNotify> children;
    //类型，0为组织架构，1为成员
    private int type;
    //头像路径
    private String imgUrl;
}
