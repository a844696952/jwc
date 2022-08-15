package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("CMS学校空间表")
public class XwCmsSchoolSpace{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("操作日期")
    private String operateTime;
    @ApiModelProperty("二级域名")
    private String secondDomain;
    @ApiModelProperty("状态 0--关闭 1--开启")
    private Integer status;
    @ApiModelProperty("学校名称")
    private String name;
    @ApiModelProperty("省份名称")
    private String provinceName;
    @ApiModelProperty("省份ID")
    private String provinceId;
    @ApiModelProperty("学校状态 42开启 43关闭")
    private String schoolStatus;
    @ApiModelProperty("学校过期时间")
    private String outTime;
    @ApiModelProperty("空间开关 0--关闭 1--开启")
    private String schoolSpaceStatus;
    @ApiModelProperty("主域名")
    private String domain;
    @ApiModelProperty("创建时间")
    private String createTime;


    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
