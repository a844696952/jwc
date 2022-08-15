package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.List;

/**
 * @author:xushu
 * @date:2019/5/8
 */
@Data
@Document
@ApiModel("通用设置")
public class CommenSettings {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "打卡范围", dataType = "String")
    private String punchCardRange;
    @ApiModelProperty(value = "移动端打卡  默认0 开启1", dataType = "String")
    private String appPunchCard;
    @ApiModelProperty(value = "统计阶梯", dataType = "String")
    private List<CensusType> censusTypes;
    @Indexed
    @ApiModelProperty(value = "学校Id", dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;

}
