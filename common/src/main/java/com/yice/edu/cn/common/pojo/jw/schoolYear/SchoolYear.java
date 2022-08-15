package com.yice.edu.cn.common.pojo.jw.schoolYear;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学年")
public class SchoolYear{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("学年起始时间")
    private Integer fromYear;
    @ApiModelProperty("学年结束时间")
    private Integer toYear;
    @ApiModelProperty("学年时间")
    private String fromTo;
    @ApiModelProperty("升班人的id")
    private String createId;
    @ApiModelProperty("升班人的名字")
    private String createName;
    @ApiModelProperty("升班时的时间")
    private String createTime;
    @ApiModelProperty("上学期开始时间")
    private String lastTermBegin;
    @ApiModelProperty("下学期开始时间")
    private String nextTermBegin;
    @ApiModelProperty("下学期结束时间")
    private String nextTermEnd;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
