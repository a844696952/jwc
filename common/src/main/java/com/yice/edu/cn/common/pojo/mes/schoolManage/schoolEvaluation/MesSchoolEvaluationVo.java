package com.yice.edu.cn.common.pojo.mes.schoolManage.schoolEvaluation;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @date 2019-8-13 15:22:55
 */
@Data
@ApiModel("学校评比")
public class MesSchoolEvaluationVo {

    @ApiModelProperty("评比周期，1按日，2按周，3按月，4按学期")
    private Integer evaluationType;

    @ApiModelProperty("学年")
    private String schoolYear;
    @ApiModelProperty("学期，0上学期，1下学期")
    private Integer term;
    @ApiModelProperty("日评比查询具体日期")
    private String searchTime;
    @ApiModelProperty("周或月数")
    private Integer number;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("学年id")
    private String schoolYearId;



    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String schoolId;
    private Integer exportType;//0--日 1--周/月/学期

}
