package com.yice.edu.cn.common.pojo.xw.kq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-01 09:26
 * @Description: 中移考勤原始数据接收类
 */
@Data
@ApiModel("中移考勤原始数据接收类")
public class KqOriginalDataTest {
    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "识别记录编号",dataType = "String")
    private String reqId;
    @ApiModelProperty(value = "人员唯一标识",dataType = "String")
    private String userId;
    @ApiModelProperty(value = "人员类型",dataType = "String")
    private String userType;
    @ApiModelProperty(value = "人员姓名",dataType = "String")
    private String userName;
    @ApiModelProperty(value = "（公司或）学校ID",dataType = "String")
    private String custId;
    @ApiModelProperty(value = "抓拍时间",dataType = "String")
    private String capturedTime;
    @ApiModelProperty(value = "抓拍图片",dataType = "String")
    private String capturedImage;
    @ApiModelProperty(value = "进出标识",dataType = "String")
    private String derectionFlag ;
    @ApiModelProperty(value = "设备类型",dataType = "String")
    private String deviceType ;
    @ApiModelProperty(value = "设备编号",dataType = "String")
    private String deviceNo ;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "抓拍消息类型 1识别记录2陌生人3查验记录",dataType = "String")
    private String capturedMessageType;
    @ApiModelProperty(value = "设备编号",dataType = "String")
    private String deviceId ;
    @ApiModelProperty(value = "设备名称",dataType = "String")
    private String deviceName ;
    @ApiModelProperty(value = "设备分组id",dataType = "String")
    private String deviceGroupId;
    @ApiModelProperty(value = "设备位置",dataType = "String")
    private String deviceLocation;




}
