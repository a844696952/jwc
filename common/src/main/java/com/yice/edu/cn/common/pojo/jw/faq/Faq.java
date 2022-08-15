package com.yice.edu.cn.common.pojo.jw.faq;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*
*
*/
@Data
public class Faq{

    @ApiModelProperty(value = "FAQ主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "问题名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "排序（默认为0）",dataType = "String")
    private String sort;
    @ApiModelProperty(value = "问题答案",dataType = "Text")
    private String answer;
    @ApiModelProperty(value = "应用终端源(0.教务1.教研2.云班牌3.家长APP4.教师APP)",dataType = "String")
//    private  int[] appsource;
    private String appsource;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
