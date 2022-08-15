package com.yice.edu.cn.common.pojo.general.region;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("地域表信息，省市区")
public class Region{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("parentId")
    private String parentId;
    @ApiModelProperty("shortName")
    private String shortName;
    @ApiModelProperty("levelType")
    private String levelType;
    @ApiModelProperty("cityCode")
    private String cityCode;
    @ApiModelProperty("zipCode")
    private String zipCode;
    @ApiModelProperty("mergerName")
    private String mergerName;
    @ApiModelProperty("lng")
    private String lng;
    @ApiModelProperty("lat")
    private String lat;
    @ApiModelProperty("pinyin")
    private String pinyin;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private List<Region> children;
}
