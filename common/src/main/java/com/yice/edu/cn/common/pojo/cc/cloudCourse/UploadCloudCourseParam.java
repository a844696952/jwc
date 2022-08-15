package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("云课堂上传录制资源参数")
public class UploadCloudCourseParam {
    @ApiModelProperty("录制文件路径")
    private String path;
    @ApiModelProperty("录制文件路径")
    private String cloudSubCourseId;
    @ApiModelProperty("文件状态 1:上传中 2:已上传")
    private Integer status;
    @ApiModelProperty("唯一标识")
    private String flag;
}
