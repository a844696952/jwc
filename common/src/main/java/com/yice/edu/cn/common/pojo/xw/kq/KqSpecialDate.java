package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author:xushu
 * @date:2019/3/1
 */
@Data
@Document
@ApiModel("考勤管理特殊日期表")
public class KqSpecialDate {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "说明", dataType = "String")
    private String explain;
    @ApiModelProperty(value = "开始时间 ", dataType = "String")
    private String startTime;
    @ApiModelProperty(value = "结束时间", dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String creatTime;
    @ApiModelProperty(value = "0 无需打卡 1 需打卡 ", dataType = "String")
    private String type;
    @ApiModelProperty(value = "打卡次数 1 一天一组 2 一天两组", dataType = "String")
    private String punchNumber;
    @ApiModelProperty(value = "打卡的规范", dataType = "String")
    private KqPunchRules punchTime;
    @Indexed
    @ApiModelProperty(value = "学校Id", dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private String msg;

}
