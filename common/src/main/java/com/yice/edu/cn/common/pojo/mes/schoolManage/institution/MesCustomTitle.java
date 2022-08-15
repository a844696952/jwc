package com.yice.edu.cn.common.pojo.mes.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("自定义称号表")
public class MesCustomTitle{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("属性名称")
    private String attrKey;
    @ApiModelProperty("属性值")
    private String attrValue;
    @ApiModelProperty("引用ID")
    private String refrenceId;
    @ApiModelProperty("（一级类型）属性类型对应公共参数表配置")
    private String attrType;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("子类型（属性类型对应公共参数表配置）")
    private String childrenType;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("达标线起始值")
    private Integer percentageBegin;
    @ApiModelProperty("达标线截止值")
    private Integer percentageEnd;
    @ApiModelProperty("关联时间状态表(未发布时为-1)")
    private String timeStatusId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
