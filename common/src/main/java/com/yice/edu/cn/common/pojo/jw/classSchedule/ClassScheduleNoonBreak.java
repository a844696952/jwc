package com.yice.edu.cn.common.pojo.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学校午休标志位置表")
public class ClassScheduleNoonBreak{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("在第几节之后出现午休标志")
    private Integer number;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("年级Id")
    private String gardeId;
    @ApiModelProperty("班级Id")
    private String classesId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
