package com.yice.edu.cn.common.pojo.mes.classManage.wxTempConfig;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("微信模板消息配置表")
public class WxTemplateConfig {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("小程序类型 1---德育小程序")
    private Integer appletesType;
    @ApiModelProperty("模板ID")
    private String templateId;
    @ApiModelProperty("模板类型 --根据创建的模板数量进行区分")
    private String templateType;
    @ApiModelProperty("模板名称")
    private String templateName;
    @ApiModelProperty("模板状态 1--启用 0---停用")
    private Integer status;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("备注信息")
    private String remark;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
