package com.yice.edu.cn.common.pojo.xw.cms;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("校务CMS头部导航表")
public class XwCmsHeaderNavigation{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学校Id")
    private String schoolId;
    @ApiModelProperty("导航名称")
    private String navigationName;
    @ApiModelProperty("链接")
    private String navigationUrl;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
