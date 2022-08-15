package com.yice.edu.cn.common.pojo.xw.zc.repairNew;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("新报修文件表")
public class RepairFile{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("报修表ID")
    private String repairId;
    @ApiModelProperty("处理 附件地址")
    private String upkeepFileUrl;
    @ApiModelProperty("处理 附件名")
    private String upkeepFileName;
    @ApiModelProperty("报废 附件地址")
    private String scrapFileUrl;
    @ApiModelProperty("报废 附件名")
    private String scrapFileName;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("库存明细id")
    private String assetsStockDetailId;
    @ApiModelProperty("创建时间")
    private String createTime;

    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
