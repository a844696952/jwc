package com.yice.edu.cn.common.pojo.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.List;

/**
 * @author:xushu
 * @date:2019/5/7
 */
@Data
@Document
@ApiModel("职工考勤")
public class WorkerKqRules {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("考勤组名称")
    private String kqGroupName;
    @ApiModelProperty("考勤组负责人姓名及id")
    private List<Teacher> kqGroupManager;
    @ApiModelProperty("下班不打卡  默认0  勾选1")
    private String noNeedCard;
    @ApiModelProperty("自动排休  默认0  勾选1")
    private String autoSortHoliday;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty(value = "打卡次数 1 一天一回 2 一天两回", dataType = "String")
    private String punchNumber;
    @ApiModelProperty(value = "打卡规范", dataType = "String")
    private PunchRules punchRules;
    @ApiModelProperty("打卡时间 1:星期日,2:星期一,3:星期二,4:星期三,5:星期四,6:星期五,7:星期六")
    private String[] punchDay;
    @ApiModelProperty(value = "0 旧规则 1 新规则 ", dataType = "String")
    private String rule;
    @ApiModelProperty("选择人员  id")
    private List<Teacher> basicWorker;
    //分页排序等
    @Transient
    @Valid
    private Pager pager;
}
