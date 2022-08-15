package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学校空间配置表")
public class XwCmsSchoolSpaceConfig{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("学校空间状态 0--关闭 1--开启")
    private Integer status;
    @ApiModelProperty("二级域名")
    private String secondDomain;
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
