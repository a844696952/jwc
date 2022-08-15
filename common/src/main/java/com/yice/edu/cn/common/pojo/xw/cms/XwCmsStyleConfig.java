package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

@Data
@ApiModel("系统风格配置表")
public class XwCmsStyleConfig{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("布局模式1--(2:1模式)2--(1:2模式)")
    private Integer layoutMode;
    @ApiModelProperty("皮肤样式")
    private String skinId;
    @ApiModelProperty("校名是否显示1--显示0--不显示")
    private Integer isShowSchoolName;
    @ApiModelProperty("学校logo是否显示1--显示0--不显示")
    private Integer isShowLogo;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("学校LOGO")
    private String schoolBadge;
    @ApiModelProperty("学校名")
    private String name;
    @ApiModelProperty("学校名")
    private String imageName;
    @ApiModelProperty("首页头图外链")
    private String url;
    @ApiModelProperty("首页头图路径")
    private String path;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}
