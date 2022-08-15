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
@ApiModel("光荣榜，管理")
public class DmHonourRoll{

    @ApiModelProperty("光荣榜，之星编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    @ApiModelProperty("班级编号")
    private String classId;
    @ApiModelProperty("班牌编号")
    private String classCardId;
    @ApiModelProperty("获奖时间")
    @Excel(name = "获奖时间")
    private String honourTime;
    @ApiModelProperty("状态，是否显示。1：显示，2：不显示")
    private Integer status;
    @ApiModelProperty("上榜理由")
    @Excel(name = "上榜理由")
    private String honourRemark;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("光荣榜类型")
    private String type;
    @ApiModelProperty("学生姓名")
    @Excel(name = "上榜学生")
    private String studentName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private String[] studentList;
    @Excel(name = "光荣榜类型")
    private String typeName;
    @ApiModelProperty("学生编号")
    private String studentId;
    @ApiModelProperty("评语")
    private String msg;
}
