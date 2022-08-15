package com.yice.edu.cn.common.pojo.jw.timetable;
import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("教师、课程不排课时间条件")
public class TimetableArrangeTime{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "jobId",dataType = "String")
    private String jobId;
    @ApiModelProperty(value = "时间片位置,上下左右,多个用逗号隔开",dataType = "String")
    private String timeSlotPos;
    @ApiModelProperty(value = "associationId",dataType = "String")
    private String associationId;
    @ApiModelProperty(value = "associationName",dataType = "String")
    private String associationName;
    @ApiModelProperty(value = "1:教师不排课;2:课程不排课",dataType = "Integer")
    private Integer type;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
