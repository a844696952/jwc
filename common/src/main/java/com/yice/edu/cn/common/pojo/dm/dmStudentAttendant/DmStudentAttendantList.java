package com.yice.edu.cn.common.pojo.dm.dmStudentAttendant;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("值日生学生列表")
public class DmStudentAttendantList{

    @ApiModelProperty("值日生学生列表编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("值日生编号")
    private String studentAttendantId;
    @ApiModelProperty("班级编号")
    private String studentId;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
