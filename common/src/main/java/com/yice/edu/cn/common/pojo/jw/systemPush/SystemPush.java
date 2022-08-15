package com.yice.edu.cn.common.pojo.jw.systemPush;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*系统推送
*
*/
@Data
public class SystemPush{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "发送对象，应用id列表",dataType = "String")
    private String[] appIds;
    @ApiModelProperty(value = "紧急程度,0一般,1紧急，2非常紧急",dataType = "Integer")
    private Integer urgent;
    @ApiModelProperty(value = "通知标题",dataType = "String")
    private String title;
    @ApiModelProperty(value = "通知内容",dataType = "String")
    private String content;
    @ApiModelProperty(value = "发布时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "用户id，教师id或者学生id",dataType = "String")
    private String userId;
    @ApiModelProperty(value = "推送扩展字段",dataType = "Extras")
    private Extras extras;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
