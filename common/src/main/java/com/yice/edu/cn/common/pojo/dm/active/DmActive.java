package com.yice.edu.cn.common.pojo.dm.active;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;

/**
 * 云班牌学校活动管理
 * 创建于2018-09-03
 * @author 陈飞龙
 */
@Data
public class DmActive{

    @ApiModelProperty(value = "编号",dataType = "String")
    private String id;
    @ApiModelProperty(value = "学校编号",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "活动名称",dataType = "String")
    private String activeName;
    @ApiModelProperty(value = "活动的封面图地址",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "活动简介",dataType = "String")
    private String content;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
    @ApiModelProperty(value = "额外字段，用于批量操作临时保存活动编号的数组",dataType = "String")
    @Transient
    private String[] rowData;
}
