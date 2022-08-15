package com.yice.edu.cn.common.pojo.jw.schoolPush;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
*
*学校推送
*
*/
@Data
@Document
public class SchoolPush{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "发送对象，应用id列表,详见JpushApp类",dataType = "String")
    @Indexed
    private int[] appIds;
    @ApiModelProperty(value = "紧急程度,0一般,1紧急，2非常紧急",dataType = "Integer")
    private Integer urgent;
    @ApiModelProperty(value = "通知标题",dataType = "String")
    private String title;
    @ApiModelProperty(value = "通知内容",dataType = "String")
    private String content;
    @ApiModelProperty(value = "发布时间",dataType = "String")
    @Indexed
    private String createTime;
    @ApiModelProperty(value = "学校id",dataType = "String")
    @Indexed
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
    //额外字段
    @Transient
    private String[] rangeTime;
    @Transient
    @ApiModelProperty(value = "应用id,JpushApp枚举",dataType = "Integer")
    private Integer appId;
}
