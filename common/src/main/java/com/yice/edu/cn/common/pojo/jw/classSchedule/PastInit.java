package com.yice.edu.cn.common.pojo.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("往期课表的节数")
public class PastInit {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("数量")
    private Integer count;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("课表关联id")
    private String scheduleId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
