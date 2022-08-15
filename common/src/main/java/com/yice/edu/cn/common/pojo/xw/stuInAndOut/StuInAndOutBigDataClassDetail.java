package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("学生出入校大数据班级详情对象")
public class StuInAndOutBigDataClassDetail {

    @ApiModelProperty(value = "班主任名称", dataType = "String")
    private String headmasterName;
    @ApiModelProperty(value = "班主任电话", dataType = "String")
    private String headmasterTel;
    @ApiModelProperty(value = "班级名称", dataType = "String")
    private String className;
    @ApiModelProperty(value = "本班缺勤人数", dataType = "String")
    private Integer classAbsentStuNum;
    @ApiModelProperty(value = "本班请假人数", dataType = "String")
    private Integer classLeaveStuNum;
}
