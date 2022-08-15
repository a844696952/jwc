package com.yice.edu.cn.common.pojo.jy.titleQuota;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;



@Data
@ApiModel("题目额度教师使用记录表")
public class HistoryTeacherAes extends BaseVo{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("教师访问数量")
    private Integer numTeacherVisits;
    @ApiModelProperty("题目id")
    private String topicId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}
