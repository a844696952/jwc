package com.yice.edu.cn.common.pojo.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ApiModel("区公文发送对象公文表")
public class PcdSend {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("是否已读,0为未读，1为已读")
    private Integer type;
    @ApiModelProperty("是否已回复，0为未回复，1为已回复")
    private Integer revert;
    @ApiModelProperty("公文id")
    private String docId;
    @ApiModelProperty("电教馆Id或者学校id")
    private String eehId;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("父类id")
    private String parentId;
    @ApiModelProperty("冗余仅供展示的公文数据")
    private PcdDoc pcdDoc;
    @ApiModelProperty("当前用户id")
    private String creataUserId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "时间段数组，Pc端用来查询时用",dataType = "String[]")
    private String[] searchTimeZone;//时间段数组
}
