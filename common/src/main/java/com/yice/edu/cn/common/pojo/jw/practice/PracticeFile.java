package com.yice.edu.cn.common.pojo.jw.practice;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class PracticeFile{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "实践id",dataType = "String")
    private String practiceId;
    @ApiModelProperty(value = "文件地址",dataType = "String")
    private String fileUrl;
    @ApiModelProperty(value = "文件名称",dataType = "String")
    private String fileName;
    @ApiModelProperty(value = "文件大小",dataType = "String")
    private String fileSize;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
