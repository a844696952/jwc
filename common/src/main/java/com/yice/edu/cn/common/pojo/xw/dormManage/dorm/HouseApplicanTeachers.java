package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("宿舍申请审批对象表")
public class HouseApplicanTeachers{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("schoolId")
    private String schoolId;
    @ApiModelProperty("createTime")
    private String createTime;
    @ApiModelProperty("申请表id")
    private String houseApplicanId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("当前权重")
    private String sort;
    @ApiModelProperty("下一权重")
    private String nextSort;
    @ApiModelProperty("理由")
    private String remark;
    @ApiModelProperty("处理时间")
    private String throughTime;
    @ApiModelProperty("当前教师的审核状态  0通过  1驳回")
    private String status;
    @ApiModelProperty("是否为宿管老师   0否   1是")
    private String type;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    private String teacherName;
    private String teacherUrl;
}
