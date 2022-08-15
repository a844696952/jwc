package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("题目额度资源操作记录表")
public class HistoryTitleQuota{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("修改内容(截止日期)")
    private String modifyContentClosingDate;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("上传凭证")
    private String uploadVouchers;
    @ApiModelProperty("操作人")
    private String operator;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("增加资源数量")
    private int increaseQuantity;//增加资源数量
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String resourceName;//平台名称
    private String ids;//平台ids;

    private String name;//学校名称
    private String address;//学校地址
    private String startTime;//开始时间
    private String endTime;//开始时间

 }
