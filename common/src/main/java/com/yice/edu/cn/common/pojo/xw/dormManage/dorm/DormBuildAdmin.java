package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;


@Data
@ApiModel("宿舍管理人员")
public class DormBuildAdmin implements Serializable {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("宿管id")
    private String staffId;
    @ApiModelProperty("宿管类型(0.宿管人员,1.宿管老师,2.查寝人员)")
    private String staffType;
    @ApiModelProperty("宿舍楼id")
    private String dormBuildId;
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

    /*---------------------------*/
    @Transient
    @ApiModelProperty("宿舍楼名称")
    private String name;

    @Transient
    @ApiModelProperty("宿管ids")
    private String[] staffIds;

    @Transient
    @ApiModelProperty("宿管ids字符串")
    private String staffIdsStr;

    @Transient
    @ApiModelProperty("宿管name字符串")
    private String staffNamesStr;

    @Transient
    @ApiModelProperty("宿管老师电话")
    private String tel;
}
