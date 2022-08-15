package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("宿舍")
public class Dorm implements Serializable {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("宿舍id")
    private String dormId;
    @ApiModelProperty("宿舍类别(1.学生宿舍,2.老师宿舍,3.宿管宿舍,4.其他人员宿舍)")
    private String dormCategory;
    @ApiModelProperty("宿舍类型(1.男生宿舍,2.女生宿舍)")
    private String dormType;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("宿舍入住人数")
    private Integer personNum = 0;
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


/*---------------------------------------------------*/
    @Transient
    @ApiModelProperty("宿舍ids")
    private List<String> dormIds;

    @Transient
    @ApiModelProperty("宿舍状态(0.未满,1.已满)")
    private String dormStatus;

    @Transient
    @ApiModelProperty("等级")
    private String level;

    @Transient
    @ApiModelProperty("楼层名称")
    private String name;

    @Transient
    @ApiModelProperty("宿舍容量")
    private int capacity;

    @Transient
    @ApiModelProperty("楼层容量")
    private int floorCapacity;
    @Transient
    @ApiModelProperty("楼层空床数")
    private int floorEmptyNum;
    @Transient
    @ApiModelProperty("楼层入住人数")
    private int floorPersonNum;

    @Transient
    private List<Dorm> children;

    @Transient
    private Boolean isNullBunk = false;

}
