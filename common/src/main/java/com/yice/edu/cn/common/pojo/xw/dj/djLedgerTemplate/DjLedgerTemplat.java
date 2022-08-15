package com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("党建台账模板")
public class DjLedgerTemplat{

    @ApiModelProperty("模板id")
    private String id;
    @ApiModelProperty("数据字典id")
    private String ddid;
    @ApiModelProperty("数据字典类型")
    private String ddidType;
    @ApiModelProperty("模板内容")
    private String templateContent;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("模板名称")
    private String templateName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty("模板类型名称")
    private String typeName;
    @Transient
    @ApiModelProperty("模板列表")
    private List<DjLedgerTemplat> children;
    @Transient
    @ApiModelProperty("模板列表")
    private String ddTypeName;
}
