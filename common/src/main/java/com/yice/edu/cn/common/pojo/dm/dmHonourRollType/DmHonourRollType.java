package com.yice.edu.cn.common.pojo.dm.dmHonourRollType;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("光荣榜类型")
public class DmHonourRollType{

    @ApiModelProperty("光荣榜类型，编号")
    private String id;
    @ApiModelProperty("学校编号")
    private String schoolId;
    @ApiModelProperty("讲师编号")
    private String teacherId;
    @ApiModelProperty("类型名称")
    private String title;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
