package com.yice.edu.cn.common.pojo.jw.department;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class DepartmentTeacher{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "departmentId",dataType = "String")
    private String departmentId;
    @ApiModelProperty(value = "teacherId",dataType = "String")
    private String teacherId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty(value = "部门名称",dataType = "String")
    private String departmentName;
}
