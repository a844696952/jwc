package com.yice.edu.cn.common.pojo.xw.searchOwner;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*寻找失主表
*
*/
@Data
public class XwSearchOwner{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "标题",dataType = "String")
    private String title;
    @ApiModelProperty(value = "物品描述",dataType = "String")
    private String goodsDescribe;
    @ApiModelProperty(value = "拾遗时间",dataType = "String")
    private String pickupTime;
    @ApiModelProperty(value = "拾遗地点",dataType = "String")
    private String pickupPlace;
    @ApiModelProperty(value = "拾遗人",dataType = "String")
    private String picker;
    @ApiModelProperty(value = "拾遗人联系方式",dataType = "String")
    private String pickupContact;
    @ApiModelProperty(value = "拾遗备注",dataType = "String")
    private String pickupRemark;
    @ApiModelProperty(value = "图片最多三张，用“，”隔开",dataType = "String")
    private String img ;
    @ApiModelProperty(value = "认领人",dataType = "String")
    private String claimant;
    @ApiModelProperty(value = "认领人联系方式",dataType = "String")
    private String claimContact;
    @ApiModelProperty(value = "认领备注",dataType = "String")
    private String claimRemark;
    @ApiModelProperty(value = "认领时间",dataType = "String")
    private String claimTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "状态( 1、待认领 2、已认领)",dataType = "String")
    private String status;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;

    private String startTime; //开始时间
    private String endTime; //结束时间
    //分页排序等
    @Transient
    private Pager pager;
    private String typeName = "寻人";
}
