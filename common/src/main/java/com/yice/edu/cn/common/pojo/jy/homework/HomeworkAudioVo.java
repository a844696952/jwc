package com.yice.edu.cn.common.pojo.jy.homework;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *音频
 *
 */
@Data
public class HomeworkAudioVo {
    @ApiModelProperty(value = "音频地址",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "时长",dataType = "String")
    private String time;
}
