package com.yice.edu.cn.common.pojo.xw.dj.partyDuty;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("党员职务管理")
public class XwPartyDuty{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("党员职务名称")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    //分页排序等
    @Transient
//    @NotNull(message = "pager不能为空")
    private Pager pager;
}
