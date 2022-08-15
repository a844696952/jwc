package com.yice.edu.cn.common.pojo.xw.workerKq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author:xushu
 * @date:2019/5/8
 */
@Data
@Document
@ApiModel("考勤打卡位置")
public class KqLocation {

    @ApiModelProperty(value = "百度地图经度",dataType = "String")
    private String lon;
    @ApiModelProperty(value = "百度地图纬度",dataType = "String")
    private String lat;
    @ApiModelProperty(value = "设备打卡位置",dataType = "String")
    private String DeviceInlocation;

}
