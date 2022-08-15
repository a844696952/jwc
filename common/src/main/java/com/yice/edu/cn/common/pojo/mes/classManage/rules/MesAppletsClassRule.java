package com.yice.edu.cn.common.pojo.mes.classManage.rules;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

import java.util.List;

@Data
@ApiModel("德育小程序班级制度表")
public class MesAppletsClassRule {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("制度id")
    private String ruleId;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("分值")
    private Integer score;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty("标签类型 0--待改进 1--表扬")
    private Integer tagType;

    @ApiModelProperty("班规制度名称")
    private String name;

    @ApiModelProperty("班规制度描述")
    private String description;

    @ApiModelProperty("图标")
    private String icon;

}
