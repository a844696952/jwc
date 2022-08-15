package com.yice.edu.cn.common.pojo.xw.xwClassifiedManagement;

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
public class XwClassifiedManagement{

    @ApiModelProperty(value = "主键ID",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识 1(正常)/2",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "分类名称",dataType = "String")
    private String cname;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remark;
    @ApiModelProperty(value = "学校ID",dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
