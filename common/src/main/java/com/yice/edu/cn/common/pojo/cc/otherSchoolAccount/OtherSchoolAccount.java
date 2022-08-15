package com.yice.edu.cn.common.pojo.cc.otherSchoolAccount;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ApiModel("外校账号")
@Document
public class OtherSchoolAccount{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("账号")
    @Indexed(unique = true)
    private String name;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("学校名称,指可能存在的外校名称")
    private String schoolName;
    @ApiModelProperty("学校id,当前账号归属的学校id")
    private String schoolId;
    @ApiModelProperty("备注名称")
    private String memo;
    @ApiModelProperty("使用期限")
    private String expireDate;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    
    
    @Transient
    @ApiModelProperty("账号状态: 1.正常 2.到期")
    private String status;
}
