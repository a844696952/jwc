package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("宿舍")
public class DormStatistics implements Serializable {

    @Transient
    @ApiModelProperty("宿舍id")
    private String dormId;
    @Transient
    @ApiModelProperty("宿舍类别(1.学生宿舍,2.老师宿舍,3.宿管宿舍,4.其他人员宿舍)")
    private String dormCategory;
    @Transient
    @ApiModelProperty("宿舍类型(1.男生宿舍,2.女生宿舍)")
    private String dormType;
    @Transient
    @ApiModelProperty("宿舍入住人数")
    private int personNum;
    @Transient
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


/*---------------------------------------------------*/
    @Transient
    @ApiModelProperty("宿舍楼栋id")
    private String dormBuildId;

    @Transient
    @ApiModelProperty("宿舍楼栋名称")
    private String dormBuildName;

    @Transient
    @ApiModelProperty("性别")
    private String sex;

    @Transient
    @ApiModelProperty("性别人数统计")
    private int sexCount;

    @Transient
    @ApiModelProperty("宿舍名称")
    private String dormName;

    @Transient
    @ApiModelProperty("楼层数")
    private String floors;

    @Transient
    @ApiModelProperty("宿舍间数")
    private int capacity;

    @Transient
    @ApiModelProperty("楼栋容量")
    private int buildCapacity;
    @Transient
    @ApiModelProperty("楼栋空床数")
    private int buildEmptyNum;
    @Transient
    @ApiModelProperty("楼栋入住人数")
    private int buildPersonNum;

    @Transient
    @ApiModelProperty("楼栋入住男生人数")
    private int buildManPersonNum;

    @Transient
    @ApiModelProperty("楼栋入住女生人数")
    private int buildWomanPersonNum;

}
