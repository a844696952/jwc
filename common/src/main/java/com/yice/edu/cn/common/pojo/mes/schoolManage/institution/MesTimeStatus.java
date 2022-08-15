package com.yice.edu.cn.common.pojo.mes.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

@Data
@ApiModel("时间状态表")
public class MesTimeStatus{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("1-生效中 0--失效")
    private Integer status;
    @ApiModelProperty("结束时间")
    private String endTime;
    private String beginTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
