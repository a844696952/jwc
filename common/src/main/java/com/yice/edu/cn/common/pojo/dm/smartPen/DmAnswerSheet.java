package com.yice.edu.cn.common.pojo.dm.smartPen;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("智能笔-答题卡")
public class DmAnswerSheet{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("厂家ID")
    private String factoryId;
    @ApiModelProperty("厂家名称")
    private String factoryName;
    @ApiModelProperty("坐标")
    private String coordinate;
    @ApiModelProperty("区域宽")
    private Double width;
    @ApiModelProperty("区域高")
    private Double height;
    @ApiModelProperty("选项名称")
    private String topicName;
    @ApiModelProperty("题目类型 1--单选题 2--判断题 3--功能操作")
    private Integer topicType;
    @ApiModelProperty("教师ID")
    private String teacherId;
    @ApiModelProperty("创建日期")
    private String createDate;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("题目类型 1--教学答题卡 2--亿教云答题卡")
    private Integer answerType;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
