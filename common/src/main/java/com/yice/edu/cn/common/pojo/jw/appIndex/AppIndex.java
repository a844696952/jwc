package com.yice.edu.cn.common.pojo.jw.appIndex;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("移动端首页")
public class AppIndex{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("唯一标识符")
    private String identify;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("0教师端app,1教师端小程序,2家长端app,3家长端小程序")
    private Integer type;
    @ApiModelProperty("第几行")
    private Integer row;
    @ApiModelProperty("第几列")
    private Integer col;
    @ApiModelProperty("0默认，1表示一定要有，不需要根据个人权限帅选")
    private Boolean required;
    @ApiModelProperty("0非固定，1固定")
    private Boolean fixed;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @ApiModelProperty("儿子")
    private List<AppIndex> children;
    @ApiModelProperty("是否显示")
    private boolean display;
    @ApiModelProperty("统计数据")
    private Object data;
    @ApiModelProperty("统计数量")
    private Object count;

}
