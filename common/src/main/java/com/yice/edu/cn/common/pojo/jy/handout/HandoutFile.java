package com.yice.edu.cn.common.pojo.jy.handout;

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
public class HandoutFile{

    @ApiModelProperty(value = "讲义文件id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "讲义id",dataType = "String")
    private String handoutId;
    @ApiModelProperty(value = "文件地址",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "文件名称",dataType = "String")
    private String filename;
    @ApiModelProperty(value = "文件顺序",dataType = "Integer")
    private Integer fileOrder;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
}
