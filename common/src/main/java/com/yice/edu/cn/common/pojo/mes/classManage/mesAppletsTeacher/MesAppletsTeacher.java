package com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("德育小程序教师表")
public class MesAppletsTeacher{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("教师ID/家长ID")
    private String objectId;
    @ApiModelProperty("德育小程序openId")
    private String openId;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("类型 1教师端2家长端")
    private Integer type;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
