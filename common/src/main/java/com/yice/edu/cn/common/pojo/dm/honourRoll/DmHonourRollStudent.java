package com.yice.edu.cn.common.pojo.dm.honourRoll;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("光荣榜，学生获得者")
public class DmHonourRollStudent{

    @ApiModelProperty("获得荣誉学生编号")
    private String id;
    @ApiModelProperty("学生编号")
    private String studentId;
    @ApiModelProperty("学生姓名")
    @Excel(name="学生姓名")
    private String studentName;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("荣誉编号")
    private String honourId;
    @ApiModelProperty("次数")
    @Excel(name="获奖次数")
    private String count;
    @ApiModelProperty("获奖时间")
    @Excel(name="获奖时间")
    private String honourTime;
    @ApiModelProperty("起始时间")
    private String startTime;
    @ApiModelProperty("终止时间")
    private String endTime;
    @ApiModelProperty("班级编号")
    private String classId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @Excel(name = "光荣榜类型")
    private String typeName;
}
