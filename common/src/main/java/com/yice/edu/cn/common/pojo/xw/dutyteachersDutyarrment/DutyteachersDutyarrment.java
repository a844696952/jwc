package com.yice.edu.cn.common.pojo.xw.dutyteachersDutyarrment;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("常规值班查询：值班教师和值班安排关联表")
public class DutyteachersDutyarrment{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("值班安排id")
    private String dutyArrmentId;
    @ApiModelProperty("值班教师id")
    private String teacherId;
    @ApiModelProperty("手机号")
    private String tel;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("教师图片路径")
    private String teacherImurl;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
