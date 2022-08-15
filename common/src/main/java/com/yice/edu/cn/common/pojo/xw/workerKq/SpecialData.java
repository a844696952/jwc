package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author:xushu
 * @date:2019/5/7
 */
@Data
@ApiModel("特殊日期")
public class SpecialData {
    @ApiModelProperty(value = "主键", dataType = "String")
    private String id;
    @ApiModelProperty(value = "说明", dataType = "String")
    private String explain;
    @ApiModelProperty(value = "开始时间 ", dataType = "String")
    private String startTime;
    @ApiModelProperty(value = "结束时间", dataType = "String")
    private String endTime;
    @ApiModelProperty(value = "更新时间", dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "0 无需打卡 1 需打卡 ", dataType = "String")
    private String type;
    @ApiModelProperty(value = "打卡次数 1 一天一回 2 一天两回", dataType = "String")
    private String punchNumber;
    @ApiModelProperty(value = "打卡规范", dataType = "String")
    private PunchRules punchRules;
    @ApiModelProperty("选择人员  id")
    private List<Teacher> specialWorker;
    @Indexed
    @ApiModelProperty(value = "学校Id", dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "考勤组名称", dataType = "String")
    private String kqGroupName;
    @ApiModelProperty(value = "考勤组Id", dataType = "String")
    private String kqGroupId;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
