package com.yice.edu.cn.common.pojo.loc;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("学校配置")
public class SchoolConfig {

    @ApiModelProperty("id,使用学校id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("url:jdbc:mysql")
    private String mysqlUrl;
    @ApiModelProperty("driver-class-name")
    private String mysqlClassName;
    @ApiModelProperty("mysql-username")
    private String mysqlUserName;
    @ApiModelProperty("mysql-password")
    private String mysqlPassWord;
    @ApiModelProperty("api.loginUrl")
    private String apiLoginUrl;
    @ApiModelProperty("api.schoolUrl")
    private String apiSchoolUrl;
    @ApiModelProperty("redis-host")
    private String redisHost;
    @ApiModelProperty("redis-port")
    private String redisPort;
    @ApiModelProperty("redis-password")
    private String redisPassword;
    @ApiModelProperty("mongodb-url")
    private String mongodbUrl;
    @ApiModelProperty("文件保存路径")
    private String resPath;
    @ApiModelProperty("redis使用第几个库--默认0，最大10")
    private Integer redisDatabase;
    @ApiModelProperty("服务器域名")
    private String applicationHost;
    @ApiModelProperty("插件路径")
    private String wirisServer;
    @ApiModelProperty("本地化学校修改接口")
    private String apiUpdateSchoolUrl;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
