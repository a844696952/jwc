package com.yice.edu.cn.common.pojo.jw.building;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("楼栋表")
public class Building{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("排序权重")
    private Integer sortNum;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("场地容量")
    private String capacity;
    @ApiModelProperty("场地类型对应的id(数据字典中取对应值)")
    private String typeId;
    @ApiModelProperty("树层级(1.楼栋 2.楼层 3.场地)")
    private String level;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("楼层数量")
    private int floors;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @Transient
    private List<Building> children;

    @ApiModelProperty("楼层数量")
    @Transient
    private int oldFloors;
    @ApiModelProperty("场地类型名称")
    @Transient
    private String typeName;

    @ApiModelProperty("存该场地所有楼层id")
    private List<String> floorIdStr;

}
