package com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("人脸识别黑名单")
public class YcVerifaceBlacklist {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("人员id")
    private String personId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("头像")
    private String imageUrl;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private String[] rangeTime;
    @Transient
    private String checkStatus="1";//校验状态
}
