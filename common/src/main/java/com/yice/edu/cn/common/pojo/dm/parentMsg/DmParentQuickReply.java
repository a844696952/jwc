package com.yice.edu.cn.common.pojo.dm.parentMsg;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("设备管理下面的云班牌中的家校互联业务。用于存储家长的快捷回复")
public class DmParentQuickReply{

    @ApiModelProperty("家长快捷回复编号")
    private String id;
    @ApiModelProperty("家长编号")
    private String parentId;
    @ApiModelProperty("快捷回复内容")
    private String content;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
