package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author:xushu
 * @date:2019/4/10
 */
@Data
@ApiModel("上课时间")
public class ClassTime {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "开始时间", dataType = "String")
    private String startTime;
    @ApiModelProperty(value = "结束时间", dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @Indexed
    @ApiModelProperty(value = "学校Id", dataType = "String")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
