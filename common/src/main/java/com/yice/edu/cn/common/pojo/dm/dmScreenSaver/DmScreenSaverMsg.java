package com.yice.edu.cn.common.pojo.dm.dmScreenSaver;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("创建者：陈飞龙。创建时间：2019-01-10。说明：湖北省武汉市蔡甸区小学的系统")
public class DmScreenSaverMsg{

    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容，包含了图片，视频地址")
    private String content;
    @ApiModelProperty("发送的类型，1：图片，2：视频")
    private Integer contentType;
    @ApiModelProperty("屏保密码")
    private String pwd;
    @ApiModelProperty("状态 1：进行中，2：未开始，3：已停止，4：草稿")
    private Integer status;
    @ApiModelProperty("发送的区域")
    private String sendArea;
    @ApiModelProperty("显示起始时间")
    private String startTime;
    @ApiModelProperty("显示终止时间")
    private String endTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty("获取表格选中的编号")
    private String rowData[];

    private Integer floor;
}
