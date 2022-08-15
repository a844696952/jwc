package com.yice.edu.cn.common.pojo.xw.routineDutyFeedback;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("常规值班反馈表")
public class RoutineDutyFeedback{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("值班表id")
    private String dutyArrmentId;
    @ApiModelProperty("执勤开始时间")
    private String dutyArrmentStartTime;
    @ApiModelProperty("执勤结束时间")
    private String dutyArrmentStartEnd;
    @ApiModelProperty("签到表id")
    private String checkedDetailId;
    @ApiModelProperty("反馈人id")
    private String teacherId;
    @ApiModelProperty("反馈人名字")
    private String teacherName;
    @ApiModelProperty("反馈人头像")
    private String teacherImgUrl;
    @ApiModelProperty("反馈时间")
    private String feedbackTime;
    @ApiModelProperty("反馈类容")
    private String feedbackText;
    @ApiModelProperty("反馈人图片")
    private String feedbackPicture;
    @ApiModelProperty("值班记录日期(app查询时)")
    private String recordDate;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
