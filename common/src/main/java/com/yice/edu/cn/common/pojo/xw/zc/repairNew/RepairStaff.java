package com.yice.edu.cn.common.pojo.xw.zc.repairNew;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*维修人员信息表
*
*/
@Data
public class RepairStaff{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "维修人名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "联系电话",dataType = "String")
    private String tel;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    /**
     * 宿舍1.1  新增字段
     */
    @ApiModelProperty(value = "维修人员 类型   1其他维修人员/2宿舍维修  ",dataType = "String")
    private String staffType;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remark;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
