package com.yice.edu.cn.common.pojo.mes.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;


@Data
@ApiModel("公共配置表")
public class MesCommonConfig{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("是否按周 0--不按 1按周")
    private Integer isByWeek;
    @ApiModelProperty("按周的值")
    private String weekValue;
    @ApiModelProperty("是否按月")
    private Integer isByMonth;
    @ApiModelProperty("按月的值")
    private String monthValue;
    @ApiModelProperty("是否学期")
    private Integer isBySemester;
    @ApiModelProperty("按学期的值")
    private String semesterValue;
    @ApiModelProperty("是否按总分")
    private Integer isByTotal;
    @ApiModelProperty("是否按一级制度")
    private Integer isFirstInstitution;
    @ApiModelProperty("是否按排名")
    private Integer isByRank;
    @ApiModelProperty("是否按达标线")
    private Integer isByReach;
    @ApiModelProperty("关联时间状态表")
    private String timeStatusId;
    @ApiModelProperty(value = "按周：1 按月：2 按年：3",dataType = "Integer")
    private Integer countType;
    @ApiModelProperty(value = "年级ID",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "达标线类型1--一级制度 0--总分",dataType = "String")
    private String reachType;
    @ApiModelProperty("制度ID")
    private String institutionId;

    @ApiModelProperty("关联制度表")
    private List<MesInstitution> mesInstitutions;
    @ApiModelProperty("关联自定义称号表")
    private Map<String,List<MesCustomTitle>> mesCustomTitleMap;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
