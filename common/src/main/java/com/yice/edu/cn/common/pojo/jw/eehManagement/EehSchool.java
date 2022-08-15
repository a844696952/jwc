package com.yice.edu.cn.common.pojo.jw.eehManagement;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("")
public class EehSchool{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("电教馆id")
    private String eehId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("类型(1:电教馆 2:教育局)")
    private String type;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    @ApiModelProperty("学校id")
    private String[] schoolIds;
    @ApiModelProperty("学校名称")
    private String  schoolName;
}
