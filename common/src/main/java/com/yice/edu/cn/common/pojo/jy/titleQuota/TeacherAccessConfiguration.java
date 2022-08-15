package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("题目额度教师访问配置")
public class TeacherAccessConfiguration{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("每日访问数量(单位:次)")
    private Integer dailyVisits;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //额外字段
    private String teacherName;//教师名称
    private String tel;//教师联系电话
    private String teacherSubject;//老师所教科目
    private String[] ids;
    //private String[] idsadd;//新增id
    //private String[] idsupdate;//修改id
}
