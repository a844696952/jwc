package com.yice.edu.cn.common.pojo.dm.schoolActive;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("活动节目表")
@ExcelTarget("dmActivityItem")
public class DmActivityItem{

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("活动ID")
    private String activityId;

    @Excel(name = "节目/项目",width = 20,orderNum = "1")
    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")

    private String schoolId;
    @ApiModelProperty("班级id")
    private String classesId;

    @ApiModelProperty(value = "班级名称",dataType = "String")
    private String className;

    @ApiModelProperty("学生名称集合")
    @Excel(name = "人员名单",width = 40,needMerge = true,orderNum = "3")
    private String names;

    @ApiModelProperty("报名人数")
    @Excel(name = "人数",width = 20,needMerge = true,orderNum = "2")
    private Integer namesCount;


    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private List<DmActivitySiginUp> dmActivitySiginUps;
}
