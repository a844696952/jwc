package com.yice.edu.cn.common.pojo.xw.zc.assetsUnit;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("资产单位")
public class AssetsUnit{

    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("单位名称")
    private String name;
    @ApiModelProperty("删除标准 del 1:2(正常：删除)")
    private Integer del;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
