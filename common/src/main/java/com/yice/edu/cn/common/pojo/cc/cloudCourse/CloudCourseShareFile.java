package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;


@Data
@ApiModel("云课堂共享文件")
public class CloudCourseShareFile{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("房间id")
    private String cloudSubCourseId;
    @ApiModelProperty("直播码")
    private String broadcastCode;
    @ApiModelProperty("文件的路径")
    private String path;
    @ApiModelProperty("文件名称")
    private String name;
    @ApiModelProperty("上传者用户id")
    private String uploadUserId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    private Pager pager;
}
