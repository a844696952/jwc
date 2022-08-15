package com.yice.edu.cn.common.pojo.mes.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("公共配置表")
public class MesSchoolEvaluation extends CurSchoolYear {
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("班级ID")
    private String classId;
    @ApiModelProperty("年级ID")
    private String gradeId;
    @ApiModelProperty("班级名")
    private String className;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("关联时间状态表")
    private String timeStatusId;
    @ApiModelProperty("表格数据")
    private List<Integer> formData;
    @ApiModelProperty("称号")
    private List<String> titles;
    @ApiModelProperty("总分")
    private Integer totalScore;




    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private Integer sortNum;
}
