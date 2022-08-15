package com.yice.edu.cn.common.pojo.dm.classes;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("班级短视频表")
public class DmClassVideo{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("视频名称")
    private String videoName;
    @ApiModelProperty("视频类型")
    private Integer videoType;
    @ApiModelProperty("视频入口 1、学校活动 2、学校大事件 3、班级信息管理")
    private Integer videoEntry;
    @ApiModelProperty("视频相对路径")
    private String videoUrl;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("视频排序")
    private Integer videoSort;
    @ApiModelProperty("视频文件大小")
    private Integer videoSize;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
