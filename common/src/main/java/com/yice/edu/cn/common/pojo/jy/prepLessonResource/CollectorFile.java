package com.yice.edu.cn.common.pojo.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("收藏者（教师或家长）与运营平台资源文件关联表")
public class CollectorFile{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("收藏者id")
    private String collectorId;
    @ApiModelProperty("章节绑定id")
    private String boundId;
    @ApiModelProperty("收藏文件id")
    private String fileId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty(value = "文件名",dataType = "String")
    private String filename;
}
