package com.yice.edu.cn.common.pojo.jw.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("cms关联账户表")
public class JwTeacherCms {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("cms 登录用户名")
    private String account;
    @ApiModelProperty("cms 登录密码")
    private String password;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("设备id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    private String loginUrl;
    @Transient
    private String encryptionPassword;
}
