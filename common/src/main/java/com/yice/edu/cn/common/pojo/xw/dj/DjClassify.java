package com.yice.edu.cn.common.pojo.xw.dj;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("党建公用类型表")
public class DjClassify{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("分类名称")
    private String classifyName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("分类类型：STUDY 学习平台     ACTIVITY 活动  CONSULT 咨询")
    private String classifyType;
    @ApiModelProperty(" 0 启用   1 禁用")
    private Integer status;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
