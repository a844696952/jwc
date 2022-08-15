package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("上课时间")
public class StuInAndOutClassTime {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("年级")
    private String[] classes;
    @ApiModelProperty("结束时间和日期")
    private List<StartTimeAndDayAndEndTime> list;
    //分页
    @Transient
    @NotNull(message = "pager不能为空")
    private Pager pager;
}
