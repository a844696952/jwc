package com.yice.edu.cn.common.pojo.xw.kq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.context.annotation.Description;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author:xushu
 * @date:2019/3/1
 */
@ApiModel(value = "打卡规范", description = "保存打卡时段需要 morningIn morningOut noonIn duskOut")
@Data
@Document
public class KqPunchRules {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "早上入校", dataType = "String")
    private String morningIn;
    @ApiModelProperty(value = "早上离校", dataType = "String")
    private String morningOut;
    @ApiModelProperty(value = "中午入校", dataType = "String")
    private String noonIn;
    @ApiModelProperty(value = "傍晚离校", dataType = "String")
    private String duskOut;
    @ApiModelProperty(value = "早上入校状态", dataType = "String")
    private String morningInStatus;
    @ApiModelProperty(value = "早上离校状态", dataType = "String")
    private String morningOutStatus;
    @ApiModelProperty(value = "中午入校状态", dataType = "String")
    private String noonInStatus;
    @ApiModelProperty(value = "傍晚离校状态", dataType = "String")
    private String duskOutStatus;


}
