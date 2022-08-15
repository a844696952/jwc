package com.yice.edu.cn.common.pojo.oa.processSort;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ApiModel("OA流程分类")
@Document
public class ProcessSort{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("分类名称")
    @Indexed
    private String sortName;
    @ApiModelProperty("app端序号")
    @Indexed
    private Integer appNum;
    @ApiModelProperty("分类备注")
    private String sortRemark;
    @ApiModelProperty("创建时间")
    @Indexed
    private String createTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    @Indexed
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
