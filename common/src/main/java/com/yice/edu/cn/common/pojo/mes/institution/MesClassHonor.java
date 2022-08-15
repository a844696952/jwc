package com.yice.edu.cn.common.pojo.mes.institution;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("班级荣誉表")
public class MesClassHonor {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("是否按周 0--按周 1--按月 2--按学期")
    private Integer isByweekOrMonthOrSemester;
    @ApiModelProperty("0--按总分 1--按一级制度")
    private Integer isByTotalOrInstitution;
    @ApiModelProperty("0--按排名 1--按达标线")
    private Integer isByRankOrReach;
    @ApiModelProperty("0--第一名 1--第二名 2--第三名")
    private Integer sortNumber;
    @ApiModelProperty("属性名称 如：第一名、文明")
    private String attrKey;
    @ApiModelProperty("属性值 如：冠军、文明之星")
    private String attrValue;
    @ApiModelProperty("关联时间状态表")
    private String timeStatusId;
    @ApiModelProperty(value = "班级ID",dataType = "String")
    private String classId;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
