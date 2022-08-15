package com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("流动红旗获得班级的列表")
public class DmMobileRedBannerList{

    @ApiModelProperty("流动红旗编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("红旗编号")
    private String redBannerId;
    @ApiModelProperty("班级编号")
    private String classId;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
