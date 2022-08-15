package com.yice.edu.cn.common.pojo.dm.classCard;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("云班牌日志表")
public class DmLog{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("教师Id")
    private String teacherId;
    @ApiModelProperty("0: 后台日志  1：设备日志")
    private Integer type;
    @ApiModelProperty("操作信息")
    private String msg;
    @ApiModelProperty("执行时间")
    private String createTime;
    @ApiModelProperty("设备状态")
    private Integer status;
    @ApiModelProperty("请求地址")
    private String url;
    @ApiModelProperty("班牌id")
    private String cardId;
    @ApiModelProperty("修改时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
