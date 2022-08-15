package com.yice.edu.cn.common.pojo.xw.clCustomMaterial;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("自定义材料表")
public class ClCustomMaterial{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("分类名称")
    private String cname;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("1-校务自定义材料，2-学情自定义材料，3-教育评价自定义材料")
    private Integer type;
    @ApiModelProperty("1-学生类别,2-教师类别")
    private Integer stuOrTea;
    @ApiModelProperty("权重")
    private Long weight;
    @ApiModelProperty("自定义材料数据")
    private List<ClCustomMaterialData> clCustomMaterialDataList;
    @ApiModelProperty("自定义材料数据数量")
    private Long count;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
