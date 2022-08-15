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
@ApiModel("宿舍Vo")
public class DormBuildVo implements Serializable {

    @Transient
    @ApiModelProperty("宿舍id")
    private String dormId;
    @Transient
    @ApiModelProperty("宿舍ids集合")
    private List<String> dormIdList;
    @Transient
    @ApiModelProperty("宿管id")
    private String staffId;
    @Transient
    @ApiModelProperty("宿舍类别(1.学生宿舍,2.老师宿舍,3.宿管宿舍,4.其他人员宿舍)")
    private String dormCategory;
    @Transient
    @ApiModelProperty("宿舍类型(1.男生宿舍,2.女生宿舍)")
    private String dormType;
    @Transient
    @ApiModelProperty("宿舍状态(0.未满,1.已满)")
    private String dormStatus;
    @Transient
    @ApiModelProperty("学校id")
    private String schoolId;
    @Transient
    @ApiModelProperty("人员入住id")
    private String id;
    @Transient
    @ApiModelProperty("楼栋id")
    private String dormBuildId;
    @Transient
    @ApiModelProperty("楼栋ids数组")
    private List<String> dormBuildIdList;
    @Transient
    @ApiModelProperty("楼层id")
    private String floorId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    @NotNull(message = "床位是否为空,默认不为空")
    private Boolean isNullBunk = false;
}
