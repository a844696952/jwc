package com.yice.edu.cn.common.pojo.dm.classCard;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("apk管理")
public class Apk{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("版本号")
    private String version;
    @ApiModelProperty("上传时间")
    private String createTime;
    @ApiModelProperty("apk包大小")
    private String apkSize;
    @ApiModelProperty("apk路径")
    private String apkUrl;
    @ApiModelProperty("apk升级内容")
    private String apkMsg;
    //分页排序等
    @Transient
    private Pager pager;
}
