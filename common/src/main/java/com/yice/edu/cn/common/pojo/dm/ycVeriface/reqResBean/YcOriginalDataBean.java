package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("亿策人脸识别原始数据接收类")
public class YcOriginalDataBean {

    @ApiModelProperty(value = "人员唯一标识",dataType = "String")
    private String userId;
    @ApiModelProperty(value = "抓拍时间",dataType = "String")
    private String capturedTime;
    @ApiModelProperty(value = "抓拍图片",dataType = "String")
    private String capturedImage;
    @ApiModelProperty(value = "设备编号",dataType = "String")
    private String deviceId ;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;

    //---
    @ApiModelProperty(value = "人员类型",dataType = "String")
    private String userType;
    @ApiModelProperty(value = "抓拍消息类型 1识别记录2陌生人3查验记录",dataType = "String")
    private String capturedMessageType;
    @ApiModelProperty(value = "进出标识",dataType = "String")
    private String derectionFlag ;
    @ApiModelProperty(value = "设备类型",dataType = "String")
    private String deviceType ;

}
