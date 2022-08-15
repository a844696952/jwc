package com.yice.edu.cn.common.pojo.dm.smartPen;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("设备管理-智能笔-铺码资源表")
public class DmCodeResource {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("图片名称")
    private String pictureName;
    @ApiModelProperty("图片地址")
    private String pictureUrl;
    @ApiModelProperty("笔记本页数")
    private Integer recordPage;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    @ApiModelProperty("创建时间开始")
    private String createDateStart;

    @Transient
    @ApiModelProperty("创建时间结束")
    private String createDateEnd;

    @Transient
    @ApiModelProperty("笔记本页数（多个逗号隔开）")
    private String recordPages;
}
