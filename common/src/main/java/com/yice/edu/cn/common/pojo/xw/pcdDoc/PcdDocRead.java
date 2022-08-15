package com.yice.edu.cn.common.pojo.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ApiModel("已读公文用户表")
public class PcdDocRead {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("公文id")
    private String docId;
    @ApiModelProperty("已读人id")
    private String createUserId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
