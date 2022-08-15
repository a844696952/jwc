package com.yice.edu.cn.common.pojo.xw.dj.djLedgerTemplate;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学校党建台账")
public class DjSchoolLedger{

    @ApiModelProperty("学校党建台账ID")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("台账内容")
    private String ledgerContent;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("台账名称")
    private String ledgerName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
