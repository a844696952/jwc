package com.yice.edu.cn.common.pojo.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("自定义材料权重表")
public class ClWeight{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("1-学生类别,2-教师类别")
    private Integer stuOrTea;
    @ApiModelProperty("1-校务自定义材料，2-学情自定义材料，3-教育评价自定义材料")
    private Integer type;
    @ApiModelProperty("权重")
    private Long weight;
    @ApiModelProperty("父类Id")
    private String parentId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
