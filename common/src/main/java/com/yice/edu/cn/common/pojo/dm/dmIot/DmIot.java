package com.yice.edu.cn.common.pojo.dm.dmIot;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("物联网表")
public class DmIot {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("序列号")
    private String rows;
    @ApiModelProperty("ip")
    private String ip;
    @ApiModelProperty("端口")
    private String port;
    @ApiModelProperty("物联平台账号")
    private String accountNumber;
    @ApiModelProperty("普通账号")
    private String ordinaryAccount;
    @ApiModelProperty("api_token")
    private String token;
    @ApiModelProperty
    private String schoolId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
