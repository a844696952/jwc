package com.yice.edu.cn.common.pojo.jw.appVersionControl;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("App版本控制")
public class AppVersionControl{

    @ApiModelProperty("主键Id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("删除标识符")
    private String del;
    @ApiModelProperty("1为IOS  2为Android版")
    private Integer type;
    @ApiModelProperty("版本号")
    private Double versionNumber;
    @ApiModelProperty("版本名称（安卓端需要）")
    private String versionName;
    @ApiModelProperty("1为教师端 2为家长端 ")
    private Integer item;
    @ApiModelProperty("下载路径")
    private String url;
    @ApiModelProperty("1为强制更新  2为不强制更新")
    private Integer flag;
    @ApiModelProperty("更新提示")
    private String updateHints;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private String[] searchTimeZone;
    private String startTime;
    private String endTime;
}
