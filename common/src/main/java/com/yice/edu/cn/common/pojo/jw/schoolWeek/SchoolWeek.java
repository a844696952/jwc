package com.yice.edu.cn.common.pojo.jw.schoolWeek;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("学周表")
public class SchoolWeek{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("第一周第一天")
    private String startTime;
    @ApiModelProperty("最后一周最后一天")
    private String endTime;
    @ApiModelProperty("本学期学周数")
    private Integer weekCount;
    @ApiModelProperty("school_year表id")
    private String schoolYearId;
    @ApiModelProperty("学年")
    private String schoolYear;
    @ApiModelProperty("学年0:上学期，1:下学期")
    private Integer termType;
    @ApiModelProperty("是否可以修改")
    private Boolean canUpdate;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
