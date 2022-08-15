package com.yice.edu.cn.common.pojo.jw.CloudMarket;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("华为云配置表")
public class CloudMarket {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("生产地址")
    private String url;
    @ApiModelProperty("客户id")
    private String customerId;
    @ApiModelProperty("客户名称")
    private String customerName;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("手机号")
    private String mobilePhone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("云市场业务id")
    private String businessId;
    @ApiModelProperty("云市场订单id")
    private String orderId;
    @ApiModelProperty("产品规格标识")
    private String skuCode;
    @ApiModelProperty("产品的Id")
    private String productId;
    @ApiModelProperty("过期时间")
    private String expireTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    private String timeStamp;
    private String authToken;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
