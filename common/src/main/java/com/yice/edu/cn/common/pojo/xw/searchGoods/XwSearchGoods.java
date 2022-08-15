package com.yice.edu.cn.common.pojo.xw.searchGoods;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*寻找物品表
*
*/
@Data
public class XwSearchGoods{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "标题",dataType = "String")
    private String title;
    @ApiModelProperty(value = "物品描述",dataType = "String")
    private String goodsDescribe;
    @ApiModelProperty(value = "丢失时间",dataType = "String")
    private String loseTime;
    @ApiModelProperty(value = "丢失地点",dataType = "String")
    private String losePlace;
    @ApiModelProperty(value = "失主",dataType = "String")
    private String loser;
    @ApiModelProperty(value = "失主联系方式",dataType = "String")
    private String loseContact;
    @ApiModelProperty(value = "丢失备注",dataType = "String")
    private String loseRemark;
    @ApiModelProperty(value = "图片最多三张，用“，”隔开",dataType = "String")
    private String img;
    @ApiModelProperty(value = "拾遗人",dataType = "String")
    private String picker;
    @ApiModelProperty(value = "拾遗人联系方式",dataType = "String")
    private String pickupContact;
    @ApiModelProperty(value = "拾遗备注",dataType = "String")
    private String pickupRemark;
    @ApiModelProperty(value = "找回时间",dataType = "String")
    private String getBackTime;
    @ApiModelProperty(value = "归还时间",dataType = "String")
    private String returnTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "状态( 1、未找回 2、未归还 3、已归还)",dataType = "String")
    private String status;
    @ApiModelProperty(value = "在班牌中不需要显示已经归还的物品")
    private String status2;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;

    private String startTime; //开始时间
    private String endTime; //结束时间
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private String typeName = "寻物";
}
