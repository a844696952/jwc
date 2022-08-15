package com.yice.edu.cn.common.pojo.jw.eduEvaluation;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.SchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("综合素质表")
public class CompareQuality{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("档案名称")
    private String name;
    @ApiModelProperty("学年")
    private SchoolYear schoolYear;
    @ApiModelProperty("学期")
    private String term;
    @ApiModelProperty("发布时间")
    private String createTime;
    @ApiModelProperty("schoolId")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
