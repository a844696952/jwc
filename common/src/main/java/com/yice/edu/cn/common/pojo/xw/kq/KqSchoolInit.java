package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学校初始化中移账号表")
public class KqSchoolInit{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校id")
    private String custId;
    @ApiModelProperty("学校联系人电话")
    private String userPhone;
    @ApiModelProperty("学校名称")
    private String userNm;
    @ApiModelProperty("中移初始化账号")
    private String userAcct;
    @ApiModelProperty("中移初始化密码")
    private String userPw;
    @ApiModelProperty("中移侧学校主键")
    private String coCode;
    @ApiModelProperty("请求参数aes密钥")
    private String key;
    @ApiModelProperty("请求源(线下交付)")
    private String requstSource;
    @ApiModelProperty("接口版本号")
    private String version;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String operation;//密钥操作类型
}
