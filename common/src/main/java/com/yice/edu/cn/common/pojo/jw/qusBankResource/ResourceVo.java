package com.yice.edu.cn.common.pojo.jw.qusBankResource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 章节或者知识点资源请求参数
 *
 */
@Data
@Accessors(chain = true)
public class ResourceVo {
    @ApiModelProperty(value = "访问哪个平台资源，参考常量 如平台：100000，校本：100001")
    @NotBlank
    private String platform;

    @ApiModelProperty(value = "参数id 可以作为科目id，可以作为教材id 看具体 用处")
    @NotBlank
    private String tempId;

    @ApiModelProperty(value = "学段id")
    private String stage;
}