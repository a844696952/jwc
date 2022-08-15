package com.yice.edu.cn.common.pojo.jw.appIndex;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学校移动端首页")
public class SchoolAppIndex{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("app_index表的id")
    private String appIndexId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("0不显示,1显示")
    private Boolean display;
    @ApiModelProperty("第几行")
    private Integer row;
    @ApiModelProperty("第几列")
    private Integer col;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
