package com.yice.edu.cn.common.pojo.mes.wxPush;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 推送记录表
 * @author xz
 */
@Data
public class WxPushDetail {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("接收人openId")
    private String touser;


    @ApiModelProperty("模板id")
    private String template_id;


    @ApiModelProperty("跳转路径")
    private String page;

    @ApiModelProperty("事件id")
    private String form_id;

    @ApiModelProperty("模板内容")
    private Map<String,WxData> data;

    @ApiModelProperty("放大")
    private String emphasis_keyword;


    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("学校id")
    private String schoolId;

    @ApiModelProperty("微信推送的返回结果参数")
    private String errCode;

    @ApiModelProperty("标记已读或者未读")
    private Boolean isRead;

    @ApiModelProperty("学生头像地址")
    private String imgUrl;

}
